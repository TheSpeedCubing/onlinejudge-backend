package top.speedcubing.onlinejudge.data.exception.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

@RestControllerAdvice
class ProblemNotfoundHandler {
    @ExceptionHandler(ProblemNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemNotfoundResponse handle(ProblemNotFoundException ex) {
        return new ProblemNotfoundResponse(ex);
    }
}