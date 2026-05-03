package ocp.chapter2.example0.functionalinterface.lamda;

@FunctionalInterface
public interface AgeCalculator {
    public abstract int calculateAge(int years);
}