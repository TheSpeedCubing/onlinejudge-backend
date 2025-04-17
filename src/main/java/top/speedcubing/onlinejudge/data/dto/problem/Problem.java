package top.speedcubing.onlinejudge.data.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

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