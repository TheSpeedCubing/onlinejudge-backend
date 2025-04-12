package top.speedcubing.onlinejudge.data.submit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class SubmitRequest {
    @Getter
    @Schema
    private Integer problemId;
    @Getter
    @Schema
    private String stdin;

    @Getter
    @Schema
    private String code;
    @Getter
    @Schema
    private String language;
}
