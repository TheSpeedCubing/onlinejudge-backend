package top.speedcubing.onlinejudge.compiler.impl;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.ICompiler;

@Service
public class JavaCompilerImpl implements ICompiler {
    @Override
    public String getExtension() {
        return "java";
    }

    @Override
    public void compile(File file) throws IOException, InterruptedException {
        Process compile = Runtime.getRuntime().exec(
                new String[]{"javac", file.getAbsolutePath()}
        );
        compile.waitFor();
    }

    @Override
    public String getExecuteCommand(File workDir) {
       return String.format(
                "./isolate --dir=%s --stdin=/box/input.txt --stdout=/box/output.txt --run -- java -cp %s Main",
                workDir.getAbsolutePath(),
                workDir.getAbsolutePath()
        );
    }
}
