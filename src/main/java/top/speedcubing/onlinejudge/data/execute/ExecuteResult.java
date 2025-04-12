package top.speedcubing.onlinejudge.data.execute;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecuteResult {

    @Schema
    private String stdout;

    @Schema
    private String stderr;

    @Schema
    private Double compileTime;

    @Schema
    private Double executeTime;
}
