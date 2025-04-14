package top.speedcubing.onlinejudge.data;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.isolate.Box;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Getter
@AllArgsConstructor
public class ExecuteSession {
    private final Box box;
    private final ExecuteRequest executeRequest;
    private final int memoryLimit;

    // ioi/isolate

    public String executeIsolateCommand(String command) throws IOException, InterruptedException {
        return ShellExecutor.execAt(getBox().getAbsTempDir(), "isolate --box-id=%d %s".formatted(box.getBoxId(), command));
    }

    public String executeInBox(String... commands) throws IOException, InterruptedException {
        return ShellExecutor.execAt(getBox().getAbsBoxDir(), commands);
    }
}
