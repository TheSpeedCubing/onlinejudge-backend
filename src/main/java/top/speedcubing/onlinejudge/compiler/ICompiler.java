package top.speedcubing.onlinejudge.compiler;

import java.io.IOException;

public interface ICompiler {
    void execute(String workDir, String tempdir, String code, String stdin, int memoryLimit) throws IOException, InterruptedException;
}
