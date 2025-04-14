package top.speedcubing.onlinejudge.data.dto.execute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.dto.compiler.CompileResult;
import top.speedcubing.onlinejudge.data.dto.run.RunResult;
import top.speedcubing.onlinejudge.isolate.Box;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecuteResult {

    @Schema
    private CompileResult compileResult;
    @Schema
    private RunResult runResult;

    @JsonIgnore
    private Box box;
}
