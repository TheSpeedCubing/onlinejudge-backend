package top.speedcubing.onlinejudge.service;

import java.io.File;
import java.io.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.CompilerManager;
import top.speedcubing.onlinejudge.compiler.ICompiler;
import top.speedcubing.onlinejudge.data.submit.SubmitResult;

@Service
public class SubmitService {

    @Autowired
    private CompilerManager compilerManager;

    public SubmitResult submit(Integer problemId, String stdin, String code, String language) {
        try {
            ICompiler compiler = compilerManager.getCompiler(language);
            // 1. Create a working directory
            File workDir = new File("isolate-temp");
            if (!workDir.exists()) workDir.mkdirs();

            // 2. Write code to a temp file
            File codeFile = new File(workDir, "Main." + compiler.getExtension());
            try (FileWriter codeWriter = new FileWriter(codeFile)) {
                codeWriter.write(code);
            }

            // 3. Write stdin to a file
            File inputFile = new File(workDir, "input.txt");
            try (FileWriter inputWriter = new FileWriter(inputFile)) {
                inputWriter.write(stdin);
            }


            String boxCommand = compiler.getExecuteCommand(workDir);

            Process run = Runtime.getRuntime().exec(new String[]{"bash", "-c", boxCommand});
            run.waitFor();

            // 6. Read output
            File outputFile = new File(workDir, "output.txt");
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
