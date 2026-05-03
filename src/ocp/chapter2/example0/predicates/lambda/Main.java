package ocp.chapter2.example0.predicates.lambda;

import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {

        //Think of Predicate<T> as
        //A rule that checks something

        Predicate<Integer> validAge = age ->age >= 18;
        System.out.println(validAge.test(18));
        System.out.println(validAge.test(15));

        Predicate<String> validStr  =  str -> str.length() > 5;
        System.out.println(validStr.test("Hirantha"));
    }
}
