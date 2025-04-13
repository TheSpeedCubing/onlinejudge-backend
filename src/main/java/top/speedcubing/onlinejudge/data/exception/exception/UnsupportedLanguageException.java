package top.speedcubing.onlinejudge.data.exception.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    private final int errorCode = 4001;

    @Schema(description = "Detailed error message")
    private final String message;

    public UnsupportedLanguageResponse(UnsupportedLanguageException e) {
        this.message = "Unsupported language: " + e.getLanguage();
    }
}