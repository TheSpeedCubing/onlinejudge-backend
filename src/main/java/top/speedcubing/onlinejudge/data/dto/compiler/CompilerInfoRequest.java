package top.speedcubing.onlinejudge.data.dto.compiler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CompilerInfoRequest {

    @Schema(example = "java")
    private String language;
}