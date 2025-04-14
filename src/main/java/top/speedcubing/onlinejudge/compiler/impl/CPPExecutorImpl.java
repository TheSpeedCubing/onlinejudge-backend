package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.ExecuteSession;
import top.speedcubing.onlinejudge.data.dto.compiler.CompileResult;
import top.speedcubing.onlinejudge.data.dto.run.RunResponse;
import top.speedcubing.onlinejudge.data.meta.Meta;
import top.speedcubing.onlinejudge.utils.FileUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
@Getter
public class CPPExecutorImpl implements IExecutor {

    private final String versionString;

    public CPPExecutorImpl() throws IOException, InterruptedException {
        this.versionString = ShellExecutor.exec("g++ -v");
    }

    @Override
    public String getSrcFileName() {
        return "main.cpp";
    }

    @Override
    public String getName() {
        return "cpp";
    }

    @Override
    public void init(ExecuteSession executeSession) throws IOException {
        FileUtils.write(executeSession.getBox().getAbsBoxDir(), "main.cpp", executeSession.getExecuteRequest().getSourceCode().getCode());
    }

    @Override
    public CompileResult compile(ExecuteSession executeSession) throws IOException, InterruptedException {
        executeSession.executeIsolateCommand("--processes --dir=/etc:noexec --meta=compile.meta --stdout=compile_stdout.txt --stderr=compile_stderr.txt --run -- /usr/bin/g++ -o out " + getSrcFileName());

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
    public RunResponse run(ExecuteSession executeSession, boolean exposeStderr) throws IOException, InterruptedException {
        executeSession.executeIsolateCommand("--processes --mem=%d --dir=/etc:noexec --meta=execute.meta --stdin=input.txt --stdout=stdout.txt --stderr=stderr.txt --run -- ./out".formatted(executeSession.getMemoryLimit()));

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
