package top.speedcubing.onlinejudge.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.compiler.LanguageSelector;
import top.speedcubing.onlinejudge.data.compile.CompileResult;
import top.speedcubing.onlinejudge.data.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.execute.ExecuteResult;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;
import top.speedcubing.onlinejudge.data.run.RunResult;
import top.speedcubing.onlinejudge.utils.FileUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
public class ExecuteService {

    @Autowired
    private LanguageSelector languageSelector;

    private int i = 0;

    public ExecuteResult execute(ExecuteRequest executeRequest) {
        try {
            IExecutor compiler = languageSelector.get(executeRequest.getSourceCode().getLanguage());

            // isolate environment
            String tempDir = "isolate-temp" + "-" + (i++) + "/";
            String absTempDir = "/app/" + tempDir;
            ShellExecutor.exec("rm -r " + absTempDir);
            ShellExecutor.exec("mkdir " + absTempDir);

            String box = ShellExecutor.execAt(absTempDir, "isolate --init");
            box = box.substring(0, box.length() - 1) + "/box/";

            // prepare executor
            ExecuteSession executeSession = new ExecuteSession(box, absTempDir, executeRequest, 5120000);
            compiler.init(executeSession);

            // prepare I/O file
            FileUtils.write(box, "input.txt", executeRequest.getStdin());
            executeSession.executeInBox("touch compile_stdout.txt");
            executeSession.executeInBox("touch compile_stderr.txt");
            executeSession.executeInBox("touch stdout.txt");
            executeSession.executeInBox("touch stderr.txt");


            // compile
            ExecuteResult executeResult = new ExecuteResult();
            CompileResult compileResult = compiler.compile(executeSession);
            executeResult.setCompileResult(compileResult);
            if (!compileResult.isSuccess()) {
                return executeResult;
            }

            // run
            RunResult runResult = compiler.run(executeSession);
            executeResult.setRunResult(runResult);

            return executeResult;
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
