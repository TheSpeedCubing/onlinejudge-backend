package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.compile.CompileResult;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;
import top.speedcubing.onlinejudge.data.meta.Meta;
import top.speedcubing.onlinejudge.data.run.RunResult;
import top.speedcubing.onlinejudge.utils.FileUtils;

@Service
public class JavaExecutorImpl implements IExecutor {

    @Override
    public void init(ExecuteSession executeSession) throws IOException {
        FileUtils.write(executeSession.getAbsBoxDir(), "Main.java", executeSession.getCode());
    }

    @Override
    public CompileResult compile(ExecuteSession executeSession) throws IOException, InterruptedException {
        executeSession.executeInTemp("isolate --processes --dir=etc --meta=compile.meta --stdout=compile_stdout.txt --stderr=compile_stderr.txt --run -- /usr/bin/javac Main.java");

        CompileResult compileResult = new CompileResult(executeSession);

        Meta meta = compileResult.getMeta();
        String exitcode = meta.get("exitcode");

        if (!exitcode.equals("0")) {
            String status = meta.get("status");
            if (status.equals("RE")) {
                compileResult.setSuccess(false);
                compileResult.setStderr(executeSession.executeInBox("cat compile_stderr.txt"));
                return compileResult;
            }
        }

        compileResult.setSuccess(true);
        compileResult.setStdout(executeSession.executeInBox("cat compile_stdout.txt"));
        return compileResult;
    }

    @Override
    public RunResult run(ExecuteSession executeSession) throws IOException, InterruptedException {
        executeSession.executeInTemp("isolate --processes --mem=%d --dir=etc --meta=execute.meta --stdin=input.txt --stdout=stdout.txt --stderr=stderr.txt --run -- /usr/bin/java -Xmx128M Main".formatted(executeSession.getMemoryLimit()));

        RunResult runResult = new RunResult(executeSession);

        Meta meta = runResult.getMeta();
        String exitcode = meta.get("exitcode");

        if (!exitcode.equals("0")) {
            String status = meta.get("status");
            if (status.equals("RE")) {
                runResult.setSuccess(false);
                runResult.setStderr(executeSession.executeInBox("cat stderr.txt"));
                return runResult;
            }
        }

        runResult.setSuccess(true);
        runResult.setStdout(executeSession.executeInBox("cat stdout.txt"));
        return runResult;
    }
}
