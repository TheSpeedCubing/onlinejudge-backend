package top.speedcubing.onlinejudge.data.execute;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import top.speedcubing.onlinejudge.utils.IOUtils;
import top.speedcubing.onlinejudge.utils.ShellExecutor;

public class ExecuteSession {
    @Getter
    private final String absBox;
    @Getter
    private final String absTempDir;
    @Getter
    private final String code;
    @Getter
    private final String stdin;
    @Getter
    private final int memoryLimit;

    private Map<String, String> compileMeta;
    private Map<String, String> executeMeta;

    public ExecuteSession(String absBox, String absTempDir, String code, String stdin, int memoryLimit) {
        this.absBox = absBox;
        this.absTempDir = absTempDir;
        this.code = code;
        this.stdin = stdin;
        this.memoryLimit = memoryLimit;
    }


    // ioi/isolate

    public void loadCompileMeta() throws IOException {
        compileMeta = new HashMap<>();

        String s = IOUtils.toString(new FileInputStream(absTempDir + "compile.meta"));
        for (String kv : s.split("\\n")) {
            compileMeta.put(kv.split(":")[0], kv.split(":")[1]);
        }
    }

    public void loadExecuteMeta() throws IOException {
        executeMeta = new HashMap<>();

        String s = IOUtils.toString(new FileInputStream(absTempDir + "execute.meta"));
        for (String kv : s.split("\\n")) {
            executeMeta.put(kv.split(":")[0], kv.split(":")[1]);
        }
    }

    public String getCompileMeta(String key) {
        return compileMeta.get(key);
    }

    public String getExecuteMeta(String key) {
        return executeMeta.get(key);
    }

    public String execute(String... commands) throws IOException, InterruptedException {
        return ShellExecutor.execAt(getAbsTempDir(), commands);
    }
}
