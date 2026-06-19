package ocp.chapter7.synchronizers.semaphore.tokenbucket;

import java.util.concurrent.*;

public class TokenBucketRateLimiter {
    private Semaphore semaphore;
    private int maxBucketSize;
    private ScheduledExecutorService scheduledExecutorService;


    public TokenBucketRateLimiter(int maxBucketSize, long refillIntervalMillis) {
        this.maxBucketSize = maxBucketSize;
        this.semaphore = new Semaphore(maxBucketSize, true); // FIFO

        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        refillToken();
                    }
                },
                refillIntervalMillis,
                refillIntervalMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public void refillToken() {
        int currentAvailable = semaphore.availablePermits();

        if (currentAvailable < maxBucketSize) {
            semaphore.release();
        }

        System.out.println(
                "[TOKEN ADDED] Available Tokens = "
                        + semaphore.availablePermits()
        );
    }

    /**
     * Try to consume one token.
     */
    public void handleRequest() throws InterruptedException {
        if (semaphore.tryAcquire(1, TimeUnit.SECONDS)) {
            System.out.println(Thread.currentThread().getName() + " ✅ Request Allowed");
        } else {
            System.out.println(Thread.currentThread().getName() + " ❌ No Tokens Available");
        }
    }

    private void shutdown() {
        scheduledExecutorService.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucketRateLimiter tokenBucketRateLimiter = new TokenBucketRateLimiter(3, 333);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    // handle Reqs
                    try {
                        tokenBucketRateLimiter.handleRequest();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Thread.sleep(100);
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        tokenBucketRateLimiter.shutdown();
    }
}
