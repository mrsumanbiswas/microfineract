// File I/O, nested try-catch, multi-catch, and finally block
// it should write every transaction to app.log

/*
{date:time..} with some formating {start}
....
....
... some logging
{end} with some formating
endl

{next date:time..} again..  <- append in app.log on each and every mechine run..
*/

package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Utility class for handling system-wide logging to a physical file.
 */
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