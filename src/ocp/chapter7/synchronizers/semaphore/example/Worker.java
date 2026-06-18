package ocp.chapter7.synchronizers.semaphore.example;

import java.util.concurrent.Semaphore;

public class Worker implements Runnable {
    private final Semaphore semaphore;

    public Worker(Semaphore semaphore) {
        this.semaphore = semaphore;

    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " is working.");
            Thread.sleep((long) (Math.random() * 1000)); // Simulate work
            System.out.println(Thread.currentThread().getName() + " has finished.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        } finally {
            semaphore.release(); // Release the permit (critical!)
        }

    }
}
