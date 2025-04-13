package top.speedcubing.onlinejudge.data.execute;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.SourceCode;

@Getter
@Setter
@AllArgsConstructor
public class ExecuteRequest {

    @Schema
    private final String stdin;

    @Schema
    private final SourceCode sourceCode;
}
