package top.speedcubing.onlinejudge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecutor {

    public static String exec(String... commands) throws InterruptedException, IOException {
        StringBuilder commandList = new StringBuilder();
        for (int i = 0; i < commands.length; i++) {
            commandList.append(commands[i]);
            if (i != commands.length - 1) {
                commandList.append(" && ");
            }
        }
        System.out.println(commandList);
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", commandList.toString());
        Process process = processBuilder.start();

        // Capture the output with a BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.lineSeparator());
        }

        int code = process.waitFor();
        System.out.println(code + " " +output);
        return output.toString();
    }
}
