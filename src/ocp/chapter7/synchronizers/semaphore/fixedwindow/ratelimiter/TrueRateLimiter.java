package ocp.chapter7.synchronizers.semaphore.fixedwindow.ratelimiter;

import java.util.concurrent.*;

/**
 * TrueRateLimiter
 *
 * This class implements a simple time-based rate limiter.
 *
 * RULE:
 * - Allow only "maxRequestsPerSecond" requests per second
 * - After every 1 second, permits are refilled back to max
 *
 * IMPLEMENTATION:
 * - Uses Semaphore to control access
 * - Uses ScheduledExecutorService to refill permits every second
 */
public class TrueRateLimiter {

    // Semaphore controls how many requests can pass at a time
    private Semaphore semaphore;

    // Maximum allowed requests per second
    private int maxPermits;

    // Background scheduler responsible for refilling permits every second
    private ScheduledExecutorService refiller;

    /**
     * Constructor initializes rate limiter
     *
     * @param maxRequestsPerSecond maximum requests allowed per second
     */
    public TrueRateLimiter(int maxRequestsPerSecond) {

        // Set maximum permit limit
        this.maxPermits = maxRequestsPerSecond;

        // Create semaphore with initial permits equal to max limit
        // "true" ensures fairness (FIFO order for waiting threads)
        this.semaphore = new Semaphore(maxRequestsPerSecond, true);

        // Create a single-thread scheduler for periodic refill task
        refiller = Executors.newScheduledThreadPool(1);

        // Schedule refill task:
        // - Initial delay: 1 second
        // - Repeat every 1 second
        refiller.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        refillPermits();
                    }
                },
                1,
                1,
                TimeUnit.SECONDS
        );
    }

    /**
     * refillPermits()
     *
     * This method restores consumed permits back to max capacity.
     *
     * Example:
     * If maxPermits = 3
     * and currentAvailable = 1
     * then we add 2 permits back.
     *
     * This ensures the rate limit resets every second.
     */
    private void refillPermits() {

        // Check how many permits are currently available
        int currentAvailable = semaphore.availablePermits();

        // Calculate missing permits needed to restore full capacity
        int permitsToAdd = maxPermits - currentAvailable;

        // Add only missing permits (avoid overflow)
        if (permitsToAdd > 0) {

            // Restore permits back into semaphore
            semaphore.release(permitsToAdd);

            // Log refill activity
            System.out.println(
                    "[REFILL] Added "
                            + permitsToAdd
                            + " permits. Total now: "
                            + semaphore.availablePermits()
            );
        }
    }

    /**
     * handleRequest()
     *
     * This method simulates an incoming API request.
     *
     * Behavior:
     * - Try to acquire a permit (within 2 seconds)
     * - If successful → request is allowed
     * - If not → request is rejected (rate limit exceeded)
     */
    public void handleRequest() {

        try {

            // Try to acquire a permit with timeout
            if (semaphore.tryAcquire(2, TimeUnit.SECONDS)) {

                // Request allowed
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

        } catch (InterruptedException e) {

            // Restore interrupt status if thread is interrupted
            Thread.currentThread().interrupt();
        }
    }

    /**
     * shutdown()
     *
     * Stops the background refill scheduler gracefully.
     */
    public void shutdown() {
        refiller.shutdown();
    }

    /**
     * MAIN METHOD (TESTING FLOW)
     *
     * Steps:
     * 1. Create rate limiter (3 requests/sec)
     * 2. Create thread pool (10 workers)
     * 3. Submit 20 requests quickly
     * 4. Shutdown executor and rate limiter
     */
    public static void main(String[] args) throws Exception {

        // Create rate limiter allowing 3 requests per second
        final TrueRateLimiter rateLimiter =
                new TrueRateLimiter(3);

        // Thread pool simulating concurrent users
        ExecutorService workerPool =
                Executors.newFixedThreadPool(10);

        // Submit 20 simulated requests
        for (int i = 1; i <= 20; i++) {

            workerPool.submit(
                    new RequestWorker(rateLimiter)
            );

            // Small delay for readability of logs (not required for logic)
            Thread.sleep(100);
        }

        // Stop accepting new tasks
        workerPool.shutdown();

        // Wait for existing tasks to finish
        workerPool.awaitTermination(
                10,
                TimeUnit.SECONDS
        );

        // Stop the refill scheduler
        rateLimiter.shutdown();

        System.out.println("Main finished.");
    }
}