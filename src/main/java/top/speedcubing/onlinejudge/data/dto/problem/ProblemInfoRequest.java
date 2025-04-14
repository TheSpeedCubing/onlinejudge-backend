package top.speedcubing.onlinejudge.data.dto.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProblemInfoRequest {
    @Schema
    private String problemId;
}
