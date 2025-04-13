package top.speedcubing.onlinejudge.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SourceCode {

    @Schema(example = "import java.util.Scanner; class Main { public static void main(String[] args) { String s = new Scanner(System.in).next(); System.out.println(\"input string: \" + s); }}")
    private final String code;

    @Schema(example = "java")
    private final String language;
}
