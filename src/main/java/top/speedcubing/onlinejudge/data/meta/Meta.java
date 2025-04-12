package top.speedcubing.onlinejudge.data.meta;

import java.util.HashMap;
import java.util.Map;

public class Meta {
    private final Map<String, String> map = new HashMap<>();

    public Meta(String s) {
        for (String kv : s.split("\\n")) {
            map.put(kv.split(":")[0], kv.split(":")[1]);
        }
    }
    public String get(String key) {
        return map.get(key);
    }
}
