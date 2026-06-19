package ocp.chapter7.synchronizers.semaphore.fixedwindow.ratelimiter;

public class RequestWorker implements Runnable {

    private TrueRateLimiter rateLimiter;

    public RequestWorker(TrueRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void run() {
        rateLimiter.handleRequest();
    }
}
