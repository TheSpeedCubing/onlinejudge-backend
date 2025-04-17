package top.speedcubing.onlinejudge.compiler;

import java.io.IOException;
import top.speedcubing.onlinejudge.data.ExecuteSession;

public interface IExecutor {

    String getVersionString();

    String getSrcFileName();

    String getName();
    String getCompileCommand();
    String getRunCommand();
    void init(ExecuteSession executeSession) throws IOException;

    boolean compile(ExecuteSession executeSession) throws IOException, InterruptedException;

    boolean run(ExecuteSession executeSession, boolean exposeStderr) throws IOException, InterruptedException;
}
