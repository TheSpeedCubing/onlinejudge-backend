package top.speedcubing.onlinejudge.data.execute;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.compile.CompileResult;
import top.speedcubing.onlinejudge.data.run.RunResult;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecuteResult {

    @Schema
    private CompileResult compileResult;
    @Schema
    private RunResult runResult;
}
