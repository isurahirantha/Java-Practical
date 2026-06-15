package ocp.chapter7.synchronizers.latch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpaceLaunchSystem {
    private final static int PRE_CHECK_SYSTEMS = 3;

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(PRE_CHECK_SYSTEMS);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {

            List<SystemCheck> systems = new ArrayList<SystemCheck>();
            systems.add(new SystemCheck("Fuel System", 2, latch));
            systems.add(new SystemCheck("Telemetry", 4, latch));
            systems.add(new SystemCheck("Navigation Software", 1, latch));


            for (SystemCheck system : systems) {
                executor.submit(system);
            }

            System.out.println("Main Control: Waiting for all systems to report READY...");

            // 3. Main thread waits here until the count reaches 0
            latch.await();

            System.out.println("\n--- MAIN CONTROL: ALL SYSTEMS GO! ---");
            System.out.println("Initiating Main Engine Ignition. 3... 2... 1... Liftoff! 🚀");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Launch aborted due to interruption.");
        } finally {
            // CRUCIAL: Always shut down your executor to let the JVM exit smoothly
            executor.shutdown();
        }
    }
}
