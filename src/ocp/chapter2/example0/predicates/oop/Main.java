package ocp.chapter2.example0.predicates.oop;

import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {


        //Think of Predicate<T> as
        //A rule that checks something
        //This is with anonymous class

        Predicate<Integer> validAge = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer>=18;
            }
        };

        System.out.println(validAge.test(20));
        System.out.println(validAge.test(16));

    }
}
