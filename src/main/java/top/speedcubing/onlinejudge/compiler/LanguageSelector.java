package top.speedcubing.onlinejudge.compiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.impl.JavaExecutorImpl;
import top.speedcubing.onlinejudge.exception.exception.UnsupportedLanguageException;

@Service
public class LanguageSelector {
    @Autowired
    private JavaExecutorImpl javaCompiler;

    public IExecutor get(String language) {
        if (language.equalsIgnoreCase("java")) {
            return javaCompiler;
        }
        throw new UnsupportedLanguageException(language);
    }

    public void checkLanguage(String language) {
        get(language);
    }
}
