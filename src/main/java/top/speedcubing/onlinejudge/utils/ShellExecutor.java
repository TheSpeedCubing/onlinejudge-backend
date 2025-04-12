package top.speedcubing.onlinejudge.utils;

import java.io.File;
import java.io.IOException;

public class ShellExecutor {

    public static String exec(String... commands) throws InterruptedException, IOException {
        return execAt(null, commands);
    }

    public static String execAt(String workingDir, String... commands) throws InterruptedException, IOException {
        StringBuilder commandList = new StringBuilder();
        for (int i = 0; i < commands.length; i++) {
            commandList.append(commands[i]);
            if (i != commands.length - 1) {
                commandList.append(" && ");
            }
        }
        System.out.println(commandList + " -> command");
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", commandList.toString());
        if (workingDir != null)
            processBuilder.directory(new File(workingDir));
        Process process = processBuilder.start();

        String output = IOUtils.toString(process.getInputStream()) + IOUtils.toString(process.getErrorStream());

        int code = process.waitFor();
        System.out.println(code + ", " + output);
        return output.toString();
    }
}
