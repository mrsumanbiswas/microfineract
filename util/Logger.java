package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

// Logging to 'app.log'
public class Logger {
    private static final String LOG_FILE = "app.log";

    public static void log(String level, String message) {
        PrintWriter writer = null;
        try {
            try {
                // Open file in append mode (true)
                writer = new PrintWriter(new FileWriter(LOG_FILE, true));

                String timeStamp = LocalDateTime.now().toString();

                writer.println("[" + timeStamp + "] [" + level + "] " + message);
                writer.flush();

            } catch (SecurityException | IllegalArgumentException runtimeErr) {
                System.err.println("Runtime Logging Error: " + runtimeErr.getMessage());
            }

        } catch (IOException ioErr) {
            System.err.println("CRITICAL: Failed to access log file: " + ioErr.getMessage());

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}