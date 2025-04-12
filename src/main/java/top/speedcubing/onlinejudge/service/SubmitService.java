package top.speedcubing.onlinejudge.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.CompilerManager;
import top.speedcubing.onlinejudge.compiler.ICompiler;
import top.speedcubing.onlinejudge.data.submit.SubmitResult;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Service
public class SubmitService {

    @Autowired
    private CompilerManager compilerManager;
    int i = 0;

    public SubmitResult submit(Integer problemId, String stdin, String code, String language) {
        try {
            ICompiler compiler = compilerManager.getCompiler(language);

            String tempDir = "isolate-temp" + "-" + (i++);
            ShellExecutor.exec("mkdir " + tempDir);

            String box = ShellExecutor.exec("cd " + tempDir, "isolate --init");


            compiler.execute(box, tempDir, stdin, code, 5120000);


            // 6. Read output
            File outputFile = new File(box, "output.txt");
            String stdout = "";
            if (outputFile.exists()) {
                stdout = new String(java.nio.file.Files.readAllBytes(outputFile.toPath()));
            }

            return new SubmitResult(stdout);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
