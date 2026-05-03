package ocp.chapter2.example0.composition.normalway;

public class Car {
    private Engine engine = new Engine(); //has a engine
    public void start(){
        engine.start();
        System.out.println("Car started");
    }
}
