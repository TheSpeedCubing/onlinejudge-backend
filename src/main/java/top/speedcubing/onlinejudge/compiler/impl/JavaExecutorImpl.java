package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.dto.compiler.CompileResult;
import top.speedcubing.onlinejudge.data.ExecuteSession;
import top.speedcubing.onlinejudge.data.meta.Meta;
import top.speedcubing.onlinejudge.data.dto.run.RunResponse;
import top.speedcubing.onlinejudge.utils.FileUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
@Getter
public class JavaExecutorImpl implements IExecutor {

    private final String versionString;

    public JavaExecutorImpl() throws IOException, InterruptedException {
        this.versionString = ShellExecutor.exec("java -version");
    }

    @Override
    public String getVersionString() {
        return this.versionString;
    }

    @Override
    public void init(ExecuteSession executeSession) throws IOException {
        FileUtils.write(executeSession.getAbsBoxDir(), "Main.java", executeSession.getExecuteRequest().getSourceCode().getCode());
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
    public RunResponse run(ExecuteSession executeSession) throws IOException, InterruptedException {
        executeSession.executeInTemp("isolate --processes --mem=%d --dir=etc --meta=execute.meta --stdin=input.txt --stdout=stdout.txt --stderr=stderr.txt --run -- /usr/bin/java -Xmx128M Main".formatted(executeSession.getMemoryLimit()));

        RunResponse runResponse = new RunResponse(executeSession);

        Meta meta = runResponse.getMeta();
        String exitcode = meta.get("exitcode");

        if (!exitcode.equals("0")) {
            String status = meta.get("status");
            if (status.equals("RE")) {
                runResponse.setSuccess(false);
                runResponse.setStderr(executeSession.executeInBox("cat stderr.txt"));
                return runResponse;
            }
        }

        runResponse.setSuccess(true);
        runResponse.setStdout(executeSession.executeInBox("cat stdout.txt"));
        return runResponse;
    }
}
