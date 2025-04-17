package top.speedcubing.onlinejudge.data.dto.submit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResult;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitResult {

    @Setter
    @Schema
    private Verdict verdict;

    @Setter
    @Schema
    private String diff;

    @Schema
    private final ExecuteResult executeResult;

    @Schema
    private final ExecuteResult officialExecuteResult;

    public SubmitResult(ExecuteResult executeResult, Verdict verdict) {
        this(executeResult, null, verdict);
    }

    public SubmitResult(ExecuteResult executeResult, ExecuteResult officialExecuteResult) {
        this(executeResult, officialExecuteResult, null);
    }

    public SubmitResult(ExecuteResult executeResult, ExecuteResult officialExecuteResult, Verdict verdict) {
        this.executeResult = executeResult;
        this.officialExecuteResult = officialExecuteResult;
        this.verdict = verdict;
    }
}
