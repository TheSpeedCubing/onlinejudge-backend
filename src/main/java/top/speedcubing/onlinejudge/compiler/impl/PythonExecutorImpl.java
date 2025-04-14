package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.dto.compiler.CompileResult;
import top.speedcubing.onlinejudge.data.ExecuteSession;
import top.speedcubing.onlinejudge.data.dto.run.RunResponse;
import top.speedcubing.onlinejudge.data.meta.Meta;
import top.speedcubing.onlinejudge.utils.FileUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
@Getter
public class PythonExecutorImpl implements IExecutor {

    private final String versionString;

    public PythonExecutorImpl() throws IOException, InterruptedException {
        this.versionString = ShellExecutor.exec("python3 -V");
    }

    @Override
    public String getSrcFileName() {
        return "main.py";
    }

    @Override
    public String getName() {
        return "python";
    }

    @Override
    public void init(ExecuteSession executeSession) throws IOException {
        FileUtils.write(executeSession.getBox().getAbsBoxDir(), "main.py", executeSession.getExecuteRequest().getSourceCode().getCode());
    }

    @Override
    public CompileResult compile(ExecuteSession executeSession) throws IOException, InterruptedException {
        return null;
    }

    @Override
    public RunResponse run(ExecuteSession executeSession, boolean exposeStderr) throws IOException, InterruptedException {
        executeSession.executeIsolateCommand(("--processes --mem=%d --dir=/etc:noexec --meta=execute.meta --stdin=input.txt --stdout=stdout.txt --stderr=stderr.txt --run -- /usr/bin/python3 " + getSrcFileName()).formatted(executeSession.getMemoryLimit()));

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
