package top.speedcubing.onlinejudge.compiler;

import java.io.File;
import java.io.IOException;

public interface ICompiler {
    void execute(String workDir, String tempdir, String code, int memoryLimit) throws IOException, InterruptedException;
}
