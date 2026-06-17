package ocp.chapter7.synchronizers.phaser.BankTransactionApp.processor;

import ocp.chapter7.synchronizers.phaser.BankTransactionApp.io.SharedFileWriter;
import ocp.chapter7.synchronizers.phaser.BankTransactionApp.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Phaser;

public class FileProcessor implements Runnable {
    private final String threadName;
    private final String fileName;
    private final List<Transaction> transactions;
    private final Phaser phaser;

    public FileProcessor(String threadName, String fileName, List<Transaction> transactions, Phaser phaser) {
        this.threadName = threadName;
        this.fileName = fileName;
        this.transactions = transactions;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        try {
            // Phase 1: read file
            System.out.println(threadName + " reading " + fileName + " ...");
            readTransactionsFromFile();
            System.out.println(threadName + " finished reading.");
            phaser.arriveAndAwaitAdvance();


            // Phase 2: validate
            System.out.println(threadName + " validating transactions...");
            validateTransactions();
            System.out.println(threadName + " finished validation.");
            phaser.arriveAndAwaitAdvance();

            // Phase 3: write to shared file
            System.out.println(threadName + " writing valid transactions...");
            writeValidTransactions();
            System.out.println(threadName + " finished writing.");
            phaser.arriveAndAwaitAdvance();

            System.out.println(threadName + " completed all phases.");
        } catch (IOException e) {
            System.err.println(threadName + " error: " + e.getMessage());
        } finally {
            phaser.arriveAndDeregister();
        }
    }


    private void readTransactionsFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String id = parts[0].trim();
                    double amount;
                    try {
                        amount = Double.parseDouble(parts[1].trim());
                        transactions.add(new Transaction(id, amount));
                    } catch (NumberFormatException e) {
                        System.err.println(threadName + " skipped invalid amount: " + line);
                    }
                } else {
                    System.err.println(threadName + " skipped malformed line: " + line);
                }
            }
        }
        // Simulate processing delay
        sleep(500);
    }


    private void validateTransactions() {
        for (Transaction t : transactions) {
            t.setValid(t.getAmount() > 0);
        }
        // Simulate processing delay
        sleep(300);
    }

    private void writeValidTransactions() {
        for (Transaction t : transactions) {
            if (t.isValid()) {
                SharedFileWriter.appendLine(t.toString());
            }
        }
        sleep(200);
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
