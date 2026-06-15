package ocp.chapter7.synchronizers.phaser.BankTransactionApp.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SharedFileWriter {
    private static final String OUTPUT_FILE = "valid_transactions.txt";

    public static synchronized void appendLine(String line) {
        try {
            Files.write(Paths.get(OUTPUT_FILE),
                    (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void clearOutputFile() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_FILE));
        } catch (IOException e) {
            // ignore
        }
    }
}
