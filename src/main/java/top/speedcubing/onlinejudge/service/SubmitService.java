package top.speedcubing.onlinejudge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.CompilerManager;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.execute.ExecuteResult;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;
import top.speedcubing.onlinejudge.data.submit.SubmitRequest;
import top.speedcubing.onlinejudge.data.submit.SubmitResult;
import top.speedcubing.onlinejudge.data.submit.Verdict;
import top.speedcubing.onlinejudge.utils.FileUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
public class SubmitService {

    @Autowired
    private CompilerManager compilerManager;
    int i = 0;

    public SubmitResult submit(SubmitRequest request) {
        Integer problemId = request.getProblemId();

        String stdin = request.getStdin();
        String code = request.getCode();
        String language = request.getLanguage();

        try {
            // isolate environment
            String tempDir = "isolate-temp" + "-" + (i++) + "/";
            String absTempDir = "/app/" + tempDir;
            ShellExecutor.exec("rm -r " + absTempDir);
            ShellExecutor.exec("mkdir " + absTempDir);

            String box = ShellExecutor.execAt(absTempDir, "isolate --init");
            box = box.substring(0, box.length() - 1) + "/box/";

            // prepare I/O file
            FileUtils.write(box, "input.txt", stdin);
            ShellExecutor.execAt(box, "touch compile_stdout.txt");
            ShellExecutor.execAt(box, "touch compile_stderr.txt");
            ShellExecutor.execAt(box, "touch stdout.txt");
            ShellExecutor.execAt(box, "touch stderr.txt");

            // execute code
            IExecutor compiler = compilerManager.getCompiler(language);
            ExecuteSession executeSession = new ExecuteSession(box, absTempDir, code, stdin, 5120000);
            compiler.execute(executeSession);

            // compile.meta check
            String exitcode = executeSession.getCompileMeta("exitcode");
            if (!exitcode.equals("0")) {
                String status = executeSession.getCompileMeta("status");
                if (status.equals("RE")) {
                    ExecuteResult executeResult = new ExecuteResult();

                    double time = Double.parseDouble(executeSession.getCompileMeta("time"));
                    executeResult.setCompileTime(time);

                    String stderr = ShellExecutor.execAt(box,"cat compile_stderr.txt");
                    executeResult.setStderr(stderr);

                    SubmitResult submitResult = new SubmitResult();
                    submitResult.setVerdict(Verdict.CE);
                    submitResult.setExecuteResult(executeResult);

                    return submitResult;
                }
            }

            // execute.meta check

            exitcode = executeSession.getExecuteMeta("exitcode");
            if (!exitcode.equals("0")) {
                String status = executeSession.getExecuteMeta("status");
                if (status.equals("RE")) {
                    ExecuteResult executeResult = new ExecuteResult();

                    double time = Double.parseDouble(executeSession.getExecuteMeta("time"));
                    executeResult.setCompileTime(time);

                    String stderr = ShellExecutor.execAt(box,"cat stderr.txt");
                    executeResult.setStderr(stderr);

                    SubmitResult submitResult = new SubmitResult();
                    submitResult.setVerdict(Verdict.RE);
                    submitResult.setExecuteResult(executeResult);

                    return submitResult;
                }
            }

            String stdout = ShellExecutor.execAt(box,"cat stdout.txt");
            ExecuteResult executeResult = new ExecuteResult();
            executeResult.setStdout(stdout);
            executeResult.setCompileTime(Double.parseDouble(executeSession.getCompileMeta("time")));

            // return SubmitResult
            SubmitResult submitResult = new SubmitResult();
            submitResult.setExecuteResult(executeResult);
            return submitResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
