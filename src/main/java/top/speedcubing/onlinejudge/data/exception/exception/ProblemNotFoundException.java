package top.speedcubing.onlinejudge.data.exception.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.speedcubing.onlinejudge.data.exception.Convertible;
import top.speedcubing.onlinejudge.data.exception.errorresponse.ErrorResponse;

@Getter
@AllArgsConstructor
public class ProblemNotFoundException extends RuntimeException implements Convertible {
    private final String problemId;

    public ErrorResponse toResponse() {
        return new ProblemNotfoundResponse(this);
    }
}


@Getter
class ProblemNotfoundResponse extends ErrorResponse {
    @Schema(description = "Error code for unsupported language")
    private final int errorCode = 4001;

    @Schema(description = "Detailed error message")
    private final String message;

    public ProblemNotfoundResponse(ProblemNotFoundException e) {
        this.message = "Unsupported language: " + e.getProblemId();
    }
}