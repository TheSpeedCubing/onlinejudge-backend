package top.speedcubing.onlinejudge.data.dto.execute;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.speedcubing.onlinejudge.data.dto.SourceCode;

@Getter
@AllArgsConstructor
public class ExecuteRequest {

    @Schema(example = "Hello, World!")
    private String stdin;

    @Schema
    private SourceCode sourceCode;
}
