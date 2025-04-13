package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.compile.CompileResult;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;
import top.speedcubing.onlinejudge.data.run.RunResult;

public class PythonExecutorImpl implements IExecutor {
    @Override
    public void init(ExecuteSession executeSession) throws IOException {

    }

    @Override
    public CompileResult compile(ExecuteSession executeSession) throws IOException, InterruptedException {
        return null;
    }

    @Override
    public RunResult run(ExecuteSession executeSession) throws IOException, InterruptedException {
        return null;
    }
}
