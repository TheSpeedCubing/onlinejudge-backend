package top.speedcubing.onlinejudge.compiler.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.ICompiler;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

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
    public void execute(String box, String tempDir, String code, int memoryLimit) throws IOException, InterruptedException {
        File codeFile = new File(new File(box), "Main.java");
        try (FileWriter codeWriter = new FileWriter(codeFile)) {
            codeWriter.write(code);
        }

        ShellExecutor.exec("cp input.txt " + box);
        ShellExecutor.exec("touch " + box + "output.txt");

        // compile
        ShellExecutor.exec("isolate --processes -v --dir=etc --meta=compile.meta --run -- /usr/bin/javac Main.java");

        // X
        ShellExecutor.exec("cd " + tempDir, "isolate --processes -v --mem=%d --dir=etc --stdin=input.txt --stdout=output.txt --run -- /usr/bin/java -Xmx128M Main".formatted(memoryLimit));
    }
}
