package top.speedcubing.onlinejudge.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.ExecuteSession;
import top.speedcubing.onlinejudge.data.dto.compiler.CompileResult;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResult;
import top.speedcubing.onlinejudge.data.dto.run.RunResult;
import top.speedcubing.onlinejudge.data.meta.Meta;
import top.speedcubing.onlinejudge.isolate.Box;
import top.speedcubing.onlinejudge.utils.FileUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
public class ExecuteService {

    @Autowired
    private LanguageService languageService;

    private int i = 0;

    @Async("asyncThreadPoolExecutor")
    public CompletableFuture<ExecuteResult> execute(ExecuteRequest executeRequest, boolean exposeStderr) {
        try {
            IExecutor compiler = languageService.get(executeRequest.getSourceCode().getLanguage());

            // isolate environment
            i++;
            String tempDir = "isolate-temp-%d/".formatted(i);
            String absTempDir = "/app/%s".formatted(tempDir);
            ShellExecutor.exec("rm -r " + absTempDir);
            ShellExecutor.exec("mkdir " + absTempDir);

            ShellExecutor.execAt(absTempDir, "isolate --box-id=%d --init".formatted(i));
            // prepare executor
            Box box = new Box(i);
            ExecuteSession executeSession = new ExecuteSession(box, executeRequest, 5120000);
            compiler.init(executeSession);

            // prepare I/O file
            FileUtils.write(box.getAbsBoxDir() + "input.txt", executeRequest.getStdin());
            box.executeInBox("touch compile_stdout.txt");
            box.executeInBox("touch compile_stderr.txt");
            box.executeInBox("touch stdout.txt");
            box.executeInBox("touch stderr.txt");

            ExecuteResult executeResult = new ExecuteResult();
            executeResult.setBox(box);

            // compile
            if (compiler.compile(executeSession)) {

                Meta meta = executeSession.getMeta();
                boolean success = executeSession.getMeta().get("exitcode").equals("0");

                CompileResult compileResult = new CompileResult(success,
                        box.executeInBox("cat compile_stdout.txt"),
                        box.executeInBox("cat compile_stderr.txt"),
                        Double.parseDouble(meta.get("time")));

                executeResult.setCompileResult(compileResult);

                if (!success) {
                    return CompletableFuture.completedFuture(executeResult);
                }
            }

            // run
            if (compiler.run(executeSession, exposeStderr)) {

                Meta meta = executeSession.getMeta();
                boolean success = executeSession.getMeta().get("exitcode").equals("0");

                RunResult runResult = new RunResult(success,
                        box.executeInBox("cat stdout.txt"),
                        exposeStderr ? box.executeInBox("cat stderr.txt") : null,
                        Double.parseDouble(meta.get("time")));
                executeResult.setRunResult(runResult);

                if (!success) {
                    return CompletableFuture.completedFuture(executeResult);
                }
            }

            return CompletableFuture.completedFuture(executeResult);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
