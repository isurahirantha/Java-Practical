package ocp.chapter7.synchronizers.phaser.BankTransactionApp;

import ocp.chapter7.synchronizers.phaser.BankTransactionApp.model.Transaction;
import ocp.chapter7.synchronizers.phaser.BankTransactionApp.processor.FileProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class MainEntry2 {
    public static void main(String[] args) throws InterruptedException {
        // Phaser with 3 parties (3 files to process)
        Phaser phaser = new Phaser(3);

        // Create a fixed thread pool (same number as parties)
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Prepare transaction lists
        List<Transaction> listA = new ArrayList<>();
        List<Transaction> listB = new ArrayList<>();
        List<Transaction> listC = new ArrayList<>();

        // Submit tasks to the executor
        executor.submit(new FileProcessor("File-A", "D:\\Learning\\Java Practice\\src\\ocp\\chapter7\\synchronizers\\phaser\\BankTransactionApp\\transactions_A.txt", listA, phaser));
        executor.submit(new FileProcessor("File-B", "D:\\Learning\\Java Practice\\src\\ocp\\chapter7\\synchronizers\\phaser\\BankTransactionApp\\transactions_B.txt", listB, phaser));
        executor.submit(new FileProcessor("File-C", "D:\\Learning\\Java Practice\\src\\ocp\\chapter7\\synchronizers\\phaser\\BankTransactionApp\\transactions_C.txt", listC, phaser));

        // Shutdown executor – no new tasks accepted
        executor.shutdown();

        // Wait for all tasks to finish (or timeout)
        boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);
        if (finished) {
            System.out.println("\nAll tasks completed successfully.");
        } else {
            System.out.println("\nTimeout – some tasks did not finish.");
            executor.shutdownNow();
        }

        System.out.println("Valid transactions written to 'valid_transactions.txt'.");
    }
}
