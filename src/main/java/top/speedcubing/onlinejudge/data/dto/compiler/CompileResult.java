package top.speedcubing.onlinejudge.data.dto.compiler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import top.speedcubing.onlinejudge.data.ExecuteSession;
import top.speedcubing.onlinejudge.data.meta.Meta;
import top.speedcubing.onlinejudge.utils.IOUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompileResult {

    @Getter
    @Setter
    @Schema
    private boolean success = true;

    @Getter
    @Setter
    @Schema
    private String stdout;

    @Getter
    @Setter
    @Schema
    private String stderr;

    @Getter
    @Setter
    @Schema
    private Double time;

    @JsonIgnore
    private final ExecuteSession executeSession;

    public CompileResult(ExecuteSession executeSession) {
        this.executeSession = executeSession;
    }

    @JsonIgnore
    public Meta getMeta() throws IOException {
        String s = IOUtils.toString(new FileInputStream(executeSession.getBox().getAbsTempDir() + "compile.meta"));
        return new Meta(s);
    }
}
