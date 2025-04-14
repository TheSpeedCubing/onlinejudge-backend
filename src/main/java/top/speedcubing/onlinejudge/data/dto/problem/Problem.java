package top.speedcubing.onlinejudge.data.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.yaml.snakeyaml.Yaml;
import top.speedcubing.onlinejudge.data.dto.SourceCode;
import top.speedcubing.onlinejudge.exception.exception.ProblemNotFoundException;
import top.speedcubing.onlinejudge.service.LanguageService;
import top.speedcubing.onlinejudge.utils.IOUtils;

@Getter
@AllArgsConstructor
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
}