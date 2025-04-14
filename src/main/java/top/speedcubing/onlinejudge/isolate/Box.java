package top.speedcubing.onlinejudge.isolate;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
