package top.speedcubing.onlinejudge.data.dto.submit.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SubmitTestRequest extends AbstractSubmitRequest {

    @Schema(example = "Hello, World!")
    private String stdin;
}
