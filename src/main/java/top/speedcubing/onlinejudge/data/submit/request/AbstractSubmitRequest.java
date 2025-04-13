package top.speedcubing.onlinejudge.data.submit.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.SourceCode;

@Getter
@Setter
public abstract class AbstractSubmitRequest {

    @Schema(example = "1")
    private String problemId;

    @Schema
    private SourceCode sourceCode;
}
