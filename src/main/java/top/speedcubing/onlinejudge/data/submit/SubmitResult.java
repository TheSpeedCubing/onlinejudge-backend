package top.speedcubing.onlinejudge.data.submit;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.execute.ExecuteResult;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitResult {

    @Schema
    private Verdict verdict;

    @Schema
    private ExecuteResult executeResult;

    @Schema
    private ExecuteResult officialExecuteResult;

    public SubmitResult(ExecuteResult executeResult) {
        this.executeResult = executeResult;
    }
}
