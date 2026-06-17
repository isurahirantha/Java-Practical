package ocp.chapter7.callableex;

import java.util.concurrent.Callable;

public class MyReader implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = 0;
        while (i<=10) {
            i++;
            System.out.println("Value of var i: " + i);
        }
        return i;
    }
}
