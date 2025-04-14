package top.speedcubing.onlinejudge.compiler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.impl.CPPExecutorImpl;
import top.speedcubing.onlinejudge.compiler.impl.JavaExecutorImpl;
import top.speedcubing.onlinejudge.compiler.impl.PythonExecutorImpl;
import top.speedcubing.onlinejudge.exception.exception.UnsupportedLanguageException;

@Service
public class LanguageSelector {
    @Autowired
    private JavaExecutorImpl javaExecutor;
    @Autowired
    private CPPExecutorImpl cppExecutor;
    @Autowired
    private PythonExecutorImpl pythonExecutor;

    public IExecutor get(String language) {
        if (language.equalsIgnoreCase("java")) {
            return javaExecutor;
        } else if (language.equalsIgnoreCase("cpp")) {
            return cppExecutor;
        } else if (language.equalsIgnoreCase("python")) {
            return pythonExecutor;
        }
        throw new UnsupportedLanguageException(language);
    }

    public void checkLanguage(String language) {
        get(language);
    }
}
