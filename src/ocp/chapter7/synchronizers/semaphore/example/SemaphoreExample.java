package ocp.chapter7.synchronizers.semaphore.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    public static void main(String[] args) {
        ExecutorService executorService = null;
        try {
            Semaphore semaphore = new Semaphore(3,true);
            executorService = Executors.newFixedThreadPool(10);

            for (int i = 0; i < 10; i++) {
                executorService.submit(new Worker(semaphore));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            executorService.shutdown();
        }


    }
}
