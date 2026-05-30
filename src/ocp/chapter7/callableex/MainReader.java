package ocp.chapter7.callableex;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
* execute() is simpler, no result; submit() is richer, returns a Future and works with both Runnable and Callable.
* */

public class MainReader {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> value = executor.submit(new MyReader());
        executor.shutdown();
        System.out.println("MyReader finished, returned value: " + value.get());
        System.out.println("MyReader finished, returned value: " + value.isDone());
    }
}
