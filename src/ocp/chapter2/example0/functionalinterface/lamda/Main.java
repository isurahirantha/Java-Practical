package ocp.chapter2.example0.functionalinterface.lamda;

import ocp.chapter2.example0.functionalinterface.oop.AgeCalculator;

public class Main {
    public static void main(String[] args) {
        //Lambda - we pass behaviours as arguments.
        calculateAge((years) -> {return years*5;},10);
        calculateAge((years) -> {return years;},10);

        calculateAge(years -> years,10);
        calculateAge( years -> years * 5, 10);
    }

    public static void calculateAge(AgeCalculator calculator, int years) {
        System.out.println(calculator.calculateAge(years));
    }
}
