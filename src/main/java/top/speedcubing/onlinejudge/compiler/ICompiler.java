package top.speedcubing.onlinejudge.compiler;

import java.io.File;
import java.io.IOException;

public interface ICompiler {
    String getExtension();
    void compile(File file) throws IOException, InterruptedException;
    String getExecuteCommand(File workDir);
}
