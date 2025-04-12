package top.speedcubing.onlinejudge.utils;

import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
    public static String toString(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        int i;
        while ((i = inputStream.read()) != -1) {
            builder.append((char) i);
        }
        inputStream.close();
        return builder.toString();
    }
}
