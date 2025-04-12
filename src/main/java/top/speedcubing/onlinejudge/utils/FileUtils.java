package top.speedcubing.onlinejudge.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void write(String filename, String content) throws IOException {
        File inputFile = new File(filename);
        if (!inputFile.exists()) {
            inputFile.createNewFile();
        }
        try (FileWriter inputWriter = new FileWriter(inputFile)) {
            inputWriter.write(content);
        }
    }

    public static void write(String parent, String filename, String content) throws IOException {

        File inputFile = new File(parent, filename);
        if (!inputFile.exists()) {
            inputFile.createNewFile();
        }
        try (FileWriter inputWriter = new FileWriter(inputFile)) {
            inputWriter.write(content);
        }
    }
}
