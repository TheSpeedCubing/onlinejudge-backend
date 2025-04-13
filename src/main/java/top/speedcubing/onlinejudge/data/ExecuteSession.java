package top.speedcubing.onlinejudge.data;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Getter
@AllArgsConstructor
public class ExecuteSession {
    private final String absBoxDir;
    private final String absTempDir;
    private final ExecuteRequest executeRequest;
    private final int memoryLimit;

    // ioi/isolate

    public String executeInTemp(String... commands) throws IOException, InterruptedException {
        return ShellExecutor.execAt(getAbsTempDir(), commands);
    }

    public String executeInBox(String... commands) throws IOException, InterruptedException {
        return ShellExecutor.execAt(getAbsBoxDir(), commands);
    }
}
