package top.speedcubing.onlinejudge.data.exception.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.speedcubing.onlinejudge.data.exception.Convertible;
import top.speedcubing.onlinejudge.data.exception.errorresponse.ErrorResponse;
import top.speedcubing.onlinejudge.data.exception.errorresponse.ErrorResponseList;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
    private final ErrorResponseList errorResponses;

    public BadRequestException(Convertible exception) {
        errorResponses = new ErrorResponseList(exception);
    }
}

@Getter
class BadRequestResponse {
    @Schema(description = "Error code for HTTP Response")
    private final int errorCode = 400;

    @Schema
    private final long timestamp = System.currentTimeMillis();

    @Schema
    private final List<ErrorResponse> errorResponses;

    public BadRequestResponse(Convertible convertible) {
        this.errorResponses = Collections.singletonList(convertible.toResponse());
    }

    public BadRequestResponse(BadRequestException badRequestException) {
        this.errorResponses = badRequestException.getErrorResponses().asList();
    }
}

@RestControllerAdvice
class BadRequestHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestResponse handle(BadRequestException ex) {
        return new BadRequestResponse(ex);
    }
}