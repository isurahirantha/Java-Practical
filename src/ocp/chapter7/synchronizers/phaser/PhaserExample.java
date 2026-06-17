package ocp.chapter7.synchronizers.phaser;

import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) {
        // Create a Phaser with 3 initial parties (threads)
        Phaser phaser = new Phaser(3);

        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            new Thread(() -> {
                System.out.println("Thread " + taskId + " starting phase 1");
                sleep(1000); // simulate work
                System.out.println("Thread " + taskId + " finished phase 1, waiting at barrier");
                phaser.arriveAndAwaitAdvance(); // synchronize at phase 1

                System.out.println("Thread " + taskId + " starting phase 2");
                sleep(500);
                System.out.println("Thread " + taskId + " finished phase 2, waiting at barrier");
                phaser.arriveAndAwaitAdvance(); // synchronize at phase 2

                System.out.println("Thread " + taskId + " done, deregistering");
                phaser.arriveAndDeregister(); // optional: leave the phaser
            }).start();
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
