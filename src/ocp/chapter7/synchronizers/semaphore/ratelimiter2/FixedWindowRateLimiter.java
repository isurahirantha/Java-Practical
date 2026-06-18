package ocp.chapter7.synchronizers.semaphore.ratelimiter2;

import java.util.concurrent.*;

public class FixedWindowRateLimiter {

    private Semaphore semaphore;
    private int window;
    ScheduledExecutorService scheduledExecutorService = null;

    public FixedWindowRateLimiter(int window) {
        this.semaphore = new Semaphore(window, true);
        this.window = window;
        scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        refillPermits();
                    }
                }, 1, 1, TimeUnit.SECONDS
        );
    }

    public void refillPermits() {
        int currentAvailable = semaphore.availablePermits();
        int currentWindow = window - currentAvailable;

        if (currentWindow > 0) {
            semaphore.release(currentWindow);
        }
    }

    public void handleRequests(){
        try {
            if(semaphore.tryAcquire(2, TimeUnit.SECONDS)) {
                // Request allowed
                System.out.println(
                        Thread.currentThread().getName()
                                + " ✅ Request allowed."
                );

                // Simulate business processing time
                Thread.sleep(500);
            }else {

                // No permit available within timeout
                System.out.println(
                        Thread.currentThread().getName()
                                + " ❌ Rate limit exceeded."
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FixedWindowRateLimiter rateLimiter = new FixedWindowRateLimiter(3);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    rateLimiter.handleRequests();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10,TimeUnit.SECONDS);

        rateLimiter.shutdown();
    }

    private void shutdown() {
        scheduledExecutorService.shutdown();
    }
}
