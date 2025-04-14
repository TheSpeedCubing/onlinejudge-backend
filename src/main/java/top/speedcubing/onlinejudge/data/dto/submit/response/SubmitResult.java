package top.speedcubing.onlinejudge.data.dto.submit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.Verdict;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteResult;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitResult {

    @Schema
    private Verdict verdict;

    @Setter
    @Schema
    private String diff;

    @Setter
    @Schema
    private ExecuteResult executeResult;

    @Setter
    @Schema
    private ExecuteResult officialExecuteResult;
}
