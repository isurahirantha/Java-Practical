package ocp.chapter7.synchronizers.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SystemCheck implements Runnable {

    private final String systemName;
    private final int durationSeconds;
    private final CountDownLatch latch;

    public SystemCheck(String systemName, int durationSeconds, CountDownLatch latch) {
        this.systemName = systemName;
        this.durationSeconds = durationSeconds;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(systemName + ": Internal checks started.");
            // Simulating work
            TimeUnit.SECONDS.sleep(durationSeconds);
            System.out.println(systemName + ": STATUS: READY");
        } catch (InterruptedException e) {
            System.out.println(systemName + ": FAILED due to interruption");
            Thread.currentThread().interrupt();
        } finally {
            // 4. Crucial: Decrement the latch count regardless of success/failure
            latch.countDown();
            System.out.println("--> Latch count decremented. Remaining: " + latch.getCount());
        }
    }
}
