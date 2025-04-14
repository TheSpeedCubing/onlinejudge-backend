package top.speedcubing.onlinejudge.data.dto.problem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemProperties {
    private int memoryLimit; // MB
    private double timeLimit; // s
}