package ocp.chapter7.runnableex;

import java.util.concurrent.*;

public class MainReader {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new MyDBreader());
        executor.shutdown();

        ExecutorService executor2 = Executors.newFixedThreadPool(2);
        executor2.submit(new MyDBreader());
        executor2.shutdown();

        ExecutorService executor3 = Executors.newFixedThreadPool(2);
        executor3.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("MyDBreader started");
            }
        });
        executor3.shutdown();

        ExecutorService executor4 = Executors.newFixedThreadPool(2);
         Future<Integer> val = executor4.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("MyDBreader started");
                int i = 0;
                for (; i < 10; i++) {
                    System.out.println("MyDBreader executed " + i   );
                }
                return i;
            }
        });
        executor4.shutdown();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("MyDBreader finished"+val.get());

    }
}
