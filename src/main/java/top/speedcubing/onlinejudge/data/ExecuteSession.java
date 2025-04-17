package top.speedcubing.onlinejudge.data;

import java.io.FileInputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import top.speedcubing.onlinejudge.data.dto.execute.ExecuteRequest;
import top.speedcubing.onlinejudge.data.meta.Meta;
import top.speedcubing.onlinejudge.isolate.Box;
import top.speedcubing.onlinejudge.utils.IOUtils;

@Getter
@AllArgsConstructor
public class ExecuteSession {
    private final Box box;
    private final ExecuteRequest executeRequest;
    private final int memoryLimit;

    public Meta getMeta() {
        try {
            String s = IOUtils.toString(new FileInputStream(getBox().getAbsTempDir() + "execute.meta"));
            return new Meta(s);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
