package ocp.chapter7.synchronizers.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Gamer implements Runnable {
    private final String name;
    private final CyclicBarrier barrier;

    public Gamer(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            // PHASE 1: Loading Assets
            System.out.println(name + " is loading map assets...");
            TimeUnit.SECONDS.sleep((long) (Math.random() * 3 + 1)); // Random load time
            System.out.println(name + " finished loading. Waiting at barrier...");

            // Threads BLOCK here until 3 players call await()
            barrier.await();

            // PHASE 2: Playing Round 1
            System.out.println(name + " has entered the match and is fighting!");
            TimeUnit.SECONDS.sleep((long) (Math.random() * 3 + 1));
            System.out.println(name + " finished Round 1. Waiting for scoreboard...");

            // The SAME barrier is reused automatically!
            barrier.await();
            System.out.println(name + " is viewing the leaderboard.");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
