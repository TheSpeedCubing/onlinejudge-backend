package top.speedcubing.onlinejudge.data.dto.submit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResponse;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitResponse {

    @Schema
    private Verdict verdict;

    @Schema
    private ExecuteResponse executeResponse;

    @Schema
    private ExecuteResponse officialExecuteResponse;

    public SubmitResponse(ExecuteResponse executeResponse) {
        this.executeResponse = executeResponse;
    }
}
