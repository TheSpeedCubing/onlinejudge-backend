package top.speedcubing.onlinejudge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.speedcubing.onlinejudge.compiler.IExecutor;
import top.speedcubing.onlinejudge.compiler.impl.CPPExecutorImpl;
import top.speedcubing.onlinejudge.compiler.impl.JavaExecutorImpl;
import top.speedcubing.onlinejudge.compiler.impl.PythonExecutorImpl;
import top.speedcubing.onlinejudge.exception.exception.UnsupportedLanguageException;

@Service
public class LanguageService {
    @Autowired
    private JavaExecutorImpl javaExecutor;
    @Autowired
    private CPPExecutorImpl cppExecutor;
    @Autowired
    private PythonExecutorImpl pythonExecutor;

    public IExecutor get(String language) {
        if (language.equalsIgnoreCase(javaExecutor.getName())) {
            return javaExecutor;
        } else if (language.equalsIgnoreCase(cppExecutor.getName())) {
            return cppExecutor;
        } else if (language.equalsIgnoreCase(pythonExecutor.getName())) {
            return pythonExecutor;
        }
        throw new UnsupportedLanguageException(language);
    }

    public IExecutor fromFileName(String fileName) {
        if (javaExecutor.getSrcFileName().equals(fileName)) {
            return javaExecutor;
        }
        if (cppExecutor.getSrcFileName().equals(fileName)) {
            return cppExecutor;
        }
        if (pythonExecutor.getSrcFileName().equals(fileName)) {
            return pythonExecutor;
        }
        return null;
    }
}
