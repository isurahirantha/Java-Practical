package ocp.chapter2.example0.functionalinterface.oop;

public class HumanAge implements AgeCalculator {

    @Override
    public int calculateAge(int years) {
        return years * 1;
    }


    @Override
    public String toString() {
        return "Human";
    }
}
