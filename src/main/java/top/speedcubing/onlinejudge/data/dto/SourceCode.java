package top.speedcubing.onlinejudge.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SourceCode {

    @Schema(example = "import java.util.Scanner;\n\nclass Main {\n    public static void main(String[] args) {\n        String s = new Scanner(System.in).nextLine();\n        System.out.println(\"input string: \" + s);\n    }\n}\n")
    private final String code;

    @Schema(example = "java")
    private final String language;
}
