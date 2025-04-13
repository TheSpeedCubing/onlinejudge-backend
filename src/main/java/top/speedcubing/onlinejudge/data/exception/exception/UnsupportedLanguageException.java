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
public class UnsupportedLanguageException extends RuntimeException implements Convertible {
    private final String language;

    public ErrorResponse toResponse() {
        return new UnsupportedLanguageResponse(this);
    }
}


@Getter
class UnsupportedLanguageResponse extends ErrorResponse {
    @Schema(description = "Error code for unsupported language")
    private final int errorCode = 4002;

    @Schema(description = "Detailed error message")
    private final String message;

    public UnsupportedLanguageResponse(UnsupportedLanguageException e) {
        this.message = "Unsupported language: " + e.getLanguage();
    }
}

@RestControllerAdvice
class UnsupportedLanguageHandler {
    @ExceptionHandler(UnsupportedLanguageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnsupportedLanguageResponse handle(UnsupportedLanguageException ex) {
        return new UnsupportedLanguageResponse(ex);
    }
}