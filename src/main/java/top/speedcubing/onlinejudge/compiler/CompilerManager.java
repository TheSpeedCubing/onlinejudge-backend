package top.speedcubing.onlinejudge.compiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.impl.JavaCompilerImpl;

@Service
public class CompilerManager {
    @Autowired
    private JavaCompilerImpl javaCompiler;

    public ICompiler getCompiler(String language) {
        if (language.equalsIgnoreCase("java")) {
            return javaCompiler;
        }
        throw new IllegalArgumentException("Unsupported language: " + language);
    }
}
