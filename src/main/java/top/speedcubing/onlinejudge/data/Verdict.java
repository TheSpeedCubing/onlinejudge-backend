package top.speedcubing.onlinejudge.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Verdict {
    AC("Accepted"),
    WA("Wrong Answer"),
    TLE("Time Limit Exceed"),
    OLE("Output Limit Exceed"),
    MLE("Memory Limit Exceed"),
    CE("Compile Error"),
    RE("Runtime Error"),
    ARE("Answer Runtime Error"),
    PE("Presentation Error");

    private final String description;
}
