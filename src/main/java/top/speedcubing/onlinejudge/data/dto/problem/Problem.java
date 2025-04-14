package top.speedcubing.onlinejudge.data.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;
import top.speedcubing.onlinejudge.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.utils.IOUtils;

@Getter
public class Problem {
    @Schema
    private final String problemId;
    @Schema
    private String info;
    @Schema
    private String inputDescription;
    @Schema
    private String outputDescription;
    @Schema
    private String sampleInput;
    @Schema
    private String sampleOutput;
    @Schema
    private ProblemProperties problemProperties;

    public Problem(String problemId) {
        if (problemId.contains(".")) {
            throw new ProblemNotFoundException(problemId);
        }

        String baseDir = "/app/problems/" + problemId + "/";
        File problemDir = new File(baseDir);
        if (!problemDir.exists()) {
            throw new ProblemNotFoundException(problemId);
        }

        this.problemId = problemId;
        try {
            this.info = IOUtils.toString(new FileInputStream(baseDir + "problem.md"));
            this.inputDescription = IOUtils.toString(new FileInputStream(baseDir + "input.md"));
            this.outputDescription = IOUtils.toString(new FileInputStream(baseDir + "output.md"));
            this.sampleInput = IOUtils.toString(new FileInputStream(baseDir + "input.txt"));
            this.sampleOutput = IOUtils.toString(new FileInputStream(baseDir + "output.txt"));
            this.problemProperties = new Yaml().loadAs(new FileInputStream(baseDir + "peroerties.yml"), ProblemProperties.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}