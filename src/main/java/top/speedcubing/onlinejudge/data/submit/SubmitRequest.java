package top.speedcubing.onlinejudge.data.submit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitRequest {

    @Schema
    private Integer problemId;

    @Schema
    private String stdin;


    @Schema
    private String code;

    @Schema
    private String language;
}
