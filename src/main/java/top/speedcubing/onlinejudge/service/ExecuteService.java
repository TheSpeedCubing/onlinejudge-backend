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
            FileUtils.write(box.getAbsBoxDir(), "input.txt", executeRequest.getStdin());
            executeSession.executeInBox("touch compile_stdout.txt");
            executeSession.executeInBox("touch compile_stderr.txt");
            executeSession.executeInBox("touch stdout.txt");
            executeSession.executeInBox("touch stderr.txt");

            ExecuteResult executeResult = new ExecuteResult();
            executeResult.setBox(box);

            // compile
            if (compiler.compile(executeSession)) {
                CompileResult compileResult = new CompileResult(executeSession);
                executeResult.setCompileResult(compileResult);

                compileResult.setStdout(executeSession.executeInBox("cat compile_stdout.txt"));
                compileResult.setStderr(executeSession.executeInBox("cat compile_stderr.txt"));

                Meta meta = compileResult.getMeta();
                compileResult.setTime(Double.parseDouble(meta.get("time")));
                String exitcode = meta.get("exitcode");

                if (!exitcode.equals("0")) {
                    String status = meta.get("status");
                    if (status.equals("RE")) {
                        compileResult.setSuccess(false);
                        return CompletableFuture.completedFuture(executeResult);
                    }
                }
            }

            // run
            if (compiler.run(executeSession, exposeStderr)) {

                RunResult runResult = new RunResult(executeSession);
                executeResult.setRunResult(runResult);

                runResult.setStdout(executeSession.executeInBox("cat stdout.txt"));
                if (exposeStderr)
                    runResult.setStderr(executeSession.executeInBox("cat stderr.txt"));

                Meta meta = runResult.getMeta();
                runResult.setTime(Double.parseDouble(meta.get("time")));
                String exitcode = meta.get("exitcode");

                if (!exitcode.equals("0")) {
                    String status = meta.get("status");
                    if (status.equals("RE")) {
                        runResult.setSuccess(false);
                        return CompletableFuture.completedFuture(executeResult);
                    }
                }
            }

            return CompletableFuture.completedFuture(executeResult);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
