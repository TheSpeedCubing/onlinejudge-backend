package top.speedcubing.onlinejudge.data.submit.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSubmitRequest {

    @Schema
    private String problemId;

    @Schema
    private String code;

    @Schema
    private String language;
}
