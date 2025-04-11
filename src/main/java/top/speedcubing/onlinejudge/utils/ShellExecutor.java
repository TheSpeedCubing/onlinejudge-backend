package top.speedcubing.onlinejudge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecutor {

    public static String exec(String... commands) throws InterruptedException, IOException {
        // Create the ProcessBuilder for running the "isolate init" command
        StringBuilder commandList = new StringBuilder();
        for (int i = 0; i < commands.length; i++) {
            if (i != 0 && i != commands.length - 1) {
                commandList.append(" && ");
            }
            commandList.append(commands[i]);
        }
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", commandList.toString());
        Process process = processBuilder.start();

        // Capture the output with a BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.lineSeparator());
        }

        process.waitFor();
        return output.toString();
    }
}
