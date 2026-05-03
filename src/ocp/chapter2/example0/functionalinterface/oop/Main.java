package ocp.chapter2.example0.functionalinterface.oop;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<AgeCalculator> list = new ArrayList<AgeCalculator>();
        list.add(new DogAge());
        list.add(new HumanAge());
        calculateAge(list, 5);
    }

    public static void calculateAge(List<AgeCalculator> calculators, int years) {
        for (AgeCalculator calculator : calculators) {
            System.out.println(calculator.toString() +"-"+ calculator.calculateAge(years));
        }
    }
}
