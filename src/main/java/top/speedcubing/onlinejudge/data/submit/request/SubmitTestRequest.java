package top.speedcubing.onlinejudge.data.submit.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitTestRequest extends AbstractSubmitRequest {

    @Schema
    private String stdin;
}
