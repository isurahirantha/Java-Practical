package ocp.chapter7.synchronizers.semaphore.fixedwindow.ratelimiter3;

import java.util.concurrent.*;

public class FixedWindowRateLimiter2 {
    private Semaphore semaphore;
    private int windowSize;
    ScheduledExecutorService scheduledExecutorService;

    public FixedWindowRateLimiter2(int windowSize) {
        this.windowSize = windowSize;
        semaphore = new Semaphore(windowSize);

        scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        releasePermits();
                    }
                }
                , 1, 1, TimeUnit.SECONDS
        );

    }

    private void releasePermits() {
        int currentAvailable = semaphore.availablePermits();
        if (currentAvailable > windowSize) {
            // Emergency reset: drain the excess and reset to exactly windowSize
            semaphore.drainPermits(); // Removes ALL permits
            semaphore.release(windowSize);
            System.out.println("[!] RESET: Semaphore was over capacity. Reset to " + windowSize);
        } else if (currentAvailable < windowSize) {
            int releaseCount = windowSize - currentAvailable;
            semaphore.release(releaseCount);
        }
    }

    public void shutdown() {
        scheduledExecutorService.shutdown();
    }

    public void handleRequest() throws InterruptedException {
        if (semaphore.tryAcquire(10, TimeUnit.SECONDS)) {
            System.out.println(
                    Thread.currentThread().getName()
                            + " ✅ Request allowed."
            );
            // Simulate business processing time
            Thread.sleep(500);
        } else {
                // No permit available within timeout
                System.out.println(
                        Thread.currentThread().getName()
                                + " ❌ Rate limit exceeded."
                );
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FixedWindowRateLimiter2 limiter = new FixedWindowRateLimiter2(3);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        limiter.handleRequest();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        limiter.shutdown();

    }
}
