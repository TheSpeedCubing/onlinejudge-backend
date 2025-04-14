package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.ExecuteSession;
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
    public boolean compile(ExecuteSession executeSession) throws IOException, InterruptedException {
        executeSession.executeIsolateCommand("--processes --dir=/etc:noexec --meta=compile.meta --stdout=compile_stdout.txt --stderr=compile_stderr.txt --run -- /usr/bin/g++ -o out " + getSrcFileName());
        return true;
    }

    @Override
    public boolean run(ExecuteSession executeSession, boolean exposeStderr) throws IOException, InterruptedException {
        executeSession.executeIsolateCommand("--processes --mem=%d --dir=/etc:noexec --meta=execute.meta --stdin=input.txt --stdout=stdout.txt --stderr=stderr.txt --run -- ./out".formatted(executeSession.getMemoryLimit()));
        return true;
    }
}
