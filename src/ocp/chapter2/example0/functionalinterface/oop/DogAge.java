package ocp.chapter2.example0.functionalinterface.oop;

public class DogAge implements AgeCalculator {
    @Override
    public int calculateAge(int years) {
        return years * 7;
    }

    @Override
    public String toString() {
        return "Dog";
    }
}
