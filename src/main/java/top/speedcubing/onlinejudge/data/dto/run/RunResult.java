package top.speedcubing.onlinejudge.data.dto.run;

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
public class RunResult {

    @Setter
    @Getter
    @Schema
    private boolean success = true;

    @Setter
    @Getter
    @Schema
    private String stdout;

    @Setter
    @Getter
    @Schema
    private String stderr;

    @Setter
    @Getter
    @Schema
    private Double time;

    @JsonIgnore
    private final ExecuteSession executeSession;

    public RunResult(ExecuteSession executeSession) {
        this.executeSession = executeSession;
    }

    @JsonIgnore
    public Meta getMeta() throws IOException {
        String s = IOUtils.toString(new FileInputStream(executeSession.getBox().getAbsTempDir() + "execute.meta"));
        return new Meta(s);
    }
}
