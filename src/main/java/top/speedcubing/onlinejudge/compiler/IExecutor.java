package top.speedcubing.onlinejudge.compiler;

import java.io.IOException;
import top.speedcubing.onlinejudge.data.execute.ExecuteSession;

public interface IExecutor {
    void execute(ExecuteSession executeSession) throws IOException, InterruptedException;
}
