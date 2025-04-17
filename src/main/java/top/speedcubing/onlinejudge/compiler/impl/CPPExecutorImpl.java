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
    public String getCompileCommand() {
        return "g++ -o out %s";
    }

    @Override
    public String getRunCommand() {
        return "./out";
    }

    @Override
    public void init(ExecuteSession executeSession) throws IOException {
        FileUtils.write(executeSession.getBox().getAbsBoxDir() + getSrcFileName(), executeSession.getExecuteRequest().getSourceCode().getCode());
    }

    @Override
    public boolean compile(ExecuteSession executeSession) throws IOException, InterruptedException {
        executeSession.getBox().executeIsolateCommand("--processes --dir=/etc:noexec --meta=compile.meta --stdout=compile_stdout.txt --stderr=compile_stderr.txt --run -- /usr/bin/%s".formatted(getCompileCommand().formatted(getSrcFileName())));
        return true;
    }

    @Override
    public boolean run(ExecuteSession executeSession, boolean exposeStderr) throws IOException, InterruptedException {
        executeSession.getBox().executeIsolateCommand("--processes --mem=%d --dir=/etc:noexec --meta=execute.meta --stdin=input.txt --stdout=stdout.txt --stderr=stderr.txt --run -- %s".formatted(executeSession.getMemoryLimit(), getRunCommand()));
        return true;
    }
}
