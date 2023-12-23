package uk.ac.york.eng2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DockerComposeReset {


    public static void main() throws IOException, InterruptedException {
        executeCommand("docker-compose down -v");
        executeCommand("docker-compose up -d");
        // Optionally, add a delay to allow services to start
        Thread.sleep(5000);

    }
    private static void executeCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Read the output of the process
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();
        System.out.println("Command exited with code " + exitCode);
    }
}