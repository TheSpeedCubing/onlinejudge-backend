package top.speedcubing.onlinejudge.data.dto.compiler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompileResult {

    @Schema
    private final boolean success;

    @Schema
    private final String stdout;

    @Schema
    private final String stderr;

    @Schema
    private final Double time;
}
