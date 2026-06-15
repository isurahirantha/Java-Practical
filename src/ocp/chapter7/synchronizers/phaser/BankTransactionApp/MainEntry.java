package ocp.chapter7.synchronizers.phaser.BankTransactionApp;

import ocp.chapter7.synchronizers.phaser.BankTransactionApp.model.Transaction;
import ocp.chapter7.synchronizers.phaser.BankTransactionApp.processor.FileProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

public class MainEntry {
    public static void main(String[] args) {
        // Phaser with 3 initial parties (one per file)
        Phaser phaser = new Phaser(3);

        // Lists to hold transactions from each file
        List<Transaction> listA = new ArrayList<>();
        List<Transaction> listB = new ArrayList<>();
        List<Transaction> listC = new ArrayList<>();

        // Create threads
        Thread tA = new Thread(new FileProcessor("File-A", "D:\\Learning\\Java Practice\\src\\ocp\\chapter7\\synchronizers\\phaser\\BankTransactionApp\\transactions_A.txt", listA, phaser));
        Thread tB = new Thread(new FileProcessor("File-B", "D:\\Learning\\Java Practice\\src\\ocp\\chapter7\\synchronizers\\phaser\\BankTransactionApp\\transactions_B.txt", listB, phaser));
        Thread tC = new Thread(new FileProcessor("File-C", "D:\\Learning\\Java Practice\\src\\ocp\\chapter7\\synchronizers\\phaser\\BankTransactionApp\\transactions_C.txt", listC, phaser));

        // Start all threads
        tA.start();
        tB.start();
        tC.start();

        // Wait for completion (optional)
        try {
            tA.join();
            tB.join();
            tC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nAll threads finished. Valid transactions written to 'valid_transactions.txt'.");
    }
}
