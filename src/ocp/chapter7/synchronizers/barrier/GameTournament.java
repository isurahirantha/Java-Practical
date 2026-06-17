package ocp.chapter7.synchronizers.barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameTournament {
    public static void main(String[] args) {
        final int NUMBER_OF_PLAYERS = 3;

        // The barrier is set for 3 parties.
        // Optional: We can provide a Runnable to execute automatically
        // EVERY TIME the barrier is tripped (broken).
        CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_PLAYERS, () -> {
            System.out.println("\n==> BARRIER TRIPPED: All players ready. Proceeding to next stage! ==>\n");
        });

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_PLAYERS);

        try {
            executor.submit(new Gamer("Alpha", barrier));
            executor.submit(new Gamer("Bravo", barrier));
            executor.submit(new Gamer("Charlie", barrier));
        } finally {
            executor.shutdown();
        }

    }
}
