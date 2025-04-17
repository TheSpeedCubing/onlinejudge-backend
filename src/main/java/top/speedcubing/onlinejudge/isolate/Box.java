package top.speedcubing.onlinejudge.isolate;

import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

@Getter
@AllArgsConstructor
public class Box {
    private final int boxId;

    public String getAbsBoxDir() {
        return "/var/lib/isolate/%d/box/".formatted(boxId);
    }

    public String getAbsTempDir() {
        return "/app/isolate-temp-%d/".formatted(boxId);
    }

    public String executeIsolateCommand(String command) throws IOException, InterruptedException {
        return ShellExecutor.execAt(getAbsTempDir(), "isolate --box-id=%d %s".formatted(getBoxId(), command));
    }

    public String executeInBox(String... commands) throws IOException, InterruptedException {
        return ShellExecutor.execAt(getAbsBoxDir(), commands);
    }
}
