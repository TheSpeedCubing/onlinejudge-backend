package top.speedcubing.onlinejudge.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import top.speedcubing.onlinejudge.data.dto.SourceCode;
import top.speedcubing.onlinejudge.data.dto.problem.Problem;
import top.speedcubing.onlinejudge.data.dto.problem.ProblemInfoRequest;
import top.speedcubing.onlinejudge.data.dto.problem.ProblemInfoResult;
import top.speedcubing.onlinejudge.data.dto.problem.ProblemProperties;
import top.speedcubing.onlinejudge.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.utils.IOUtils;


@Service
public class ProblemService {

    @Autowired
    LanguageService languageService;

    public ProblemInfoResult search(ProblemInfoRequest request) {
        return new ProblemInfoResult(get(request.getProblemId()));
    }

    public Problem get(String problemId) {
        if (problemId.contains(".")) {
            throw new ProblemNotFoundException(problemId);
        }

        String baseDir = "/app/problems/%s/".formatted(problemId);
        File problemDir = new File(baseDir);
        if (!problemDir.exists()) {
            throw new ProblemNotFoundException(problemId);
        }

        try {
            String info = IOUtils.toString(new FileInputStream(baseDir + "problem.md"));
            String inputDescription = IOUtils.toString(new FileInputStream(baseDir + "input.md"));
            String outputDescription = IOUtils.toString(new FileInputStream(baseDir + "output.md"));
            String sampleInput = IOUtils.toString(new FileInputStream(baseDir + "input.txt"));
            String sampleOutput = IOUtils.toString(new FileInputStream(baseDir + "output.txt"));
            String property = IOUtils.toString(new FileInputStream(baseDir + "properties.yml"));
            ProblemProperties problemProperties = new Yaml().loadAs(property, ProblemProperties.class);
            return new Problem(problemId, info, inputDescription, outputDescription, sampleInput, sampleOutput, problemProperties);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public SourceCode getAnswer(Problem problem, String language) {
        try {
            File answerDir = new File("/app/problems/%s/answer/".formatted(problem.getProblemId()));

            String srcFileName = languageService.get(language).getSrcFileName();
            File answerFile = new File(answerDir, srcFileName);
            if (!answerFile.exists()) {
                String[] answerFiles = answerDir.list();
                if (answerFiles == null || answerFiles.length == 0) {
                    return null;
                }
                srcFileName = answerFiles[0];
            }
            answerFile = new File(answerDir, srcFileName);

            return new SourceCode(IOUtils.toString(new FileInputStream(answerFile)), languageService.fromFileName(srcFileName).getName());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
