package top.speedcubing.onlinejudge.compiler.impl;

import java.io.IOException;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.data.dto.compiler.CompileResult;
import top.speedcubing.onlinejudge.data.ExecuteSession;
import top.speedcubing.onlinejudge.data.dto.run.RunResponse;

public class PythonExecutorImpl implements IExecutor {
    @Override
    public void init(ExecuteSession executeSession) throws IOException {

    }

    @Override
    public CompileResult compile(ExecuteSession executeSession) throws IOException, InterruptedException {
        return null;
    }

    @Override
    public RunResponse run(ExecuteSession executeSession) throws IOException, InterruptedException {
        return null;
    }

    @Override
    public String getVersionString() {
        return "";
    }
}
