package top.speedcubing.onlinejudge.data.dto.submit.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import top.speedcubing.onlinejudge.data.dto.SourceCode;

@Getter
public abstract class AbstractSubmitRequest {

    @Schema(example = "1")
    private String problemId;

    @Schema
    private SourceCode sourceCode;
}
