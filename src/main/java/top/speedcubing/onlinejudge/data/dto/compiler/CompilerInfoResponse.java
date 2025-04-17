package top.speedcubing.onlinejudge.data.dto.compiler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompilerInfoResponse {
    private final String version;
    private final String compileCommand;
    private final String runCommand;
}
