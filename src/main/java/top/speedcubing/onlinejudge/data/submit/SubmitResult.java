package top.speedcubing.onlinejudge.data.submit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class SubmitResult {

    @Getter
    @Schema
    private final String stdout;
    @Getter
    @Schema
    private String officialStdout;

    @Getter
    @Schema
    private Verdict verdict;

    @Getter
    @Schema
    private double time;

    public SubmitResult(String stdout) {
        this.stdout = stdout;
    }
}
