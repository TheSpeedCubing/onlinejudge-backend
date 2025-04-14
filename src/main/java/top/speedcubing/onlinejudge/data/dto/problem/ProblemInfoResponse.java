package top.speedcubing.onlinejudge.data.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ProblemInfoResponse {
    @Getter
    @Schema
    private Problem problem;
}
