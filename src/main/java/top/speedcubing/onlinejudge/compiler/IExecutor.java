package top.speedcubing.onlinejudge.compiler;

import java.io.IOException;
import top.speedcubing.onlinejudge.data.dto.compiler.CompileResult;
import top.speedcubing.onlinejudge.data.ExecuteSession;
import top.speedcubing.onlinejudge.data.dto.run.RunResponse;

public interface IExecutor {
    void init(ExecuteSession executeSession) throws IOException;

    CompileResult compile(ExecuteSession executeSession) throws IOException, InterruptedException;

    RunResponse run(ExecuteSession executeSession, boolean exposeStderr) throws IOException, InterruptedException;

    String getVersionString();

    String getSrcFileName();

    String getName();
}
