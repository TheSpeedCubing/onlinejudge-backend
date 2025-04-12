package top.speedcubing.onlinejudge.compiler;

import java.io.IOException;
import top.speedcubing.onlinejudge.data.compile.CompileResult;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;
import top.speedcubing.onlinejudge.data.run.RunResult;

public interface IExecutor {
    void init(ExecuteSession executeSession) throws IOException;

    CompileResult compile(ExecuteSession executeSession) throws IOException, InterruptedException;

    RunResult run(ExecuteSession executeSession) throws IOException, InterruptedException;
}
