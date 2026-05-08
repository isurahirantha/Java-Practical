package ocp.chapter2.example0.designpatterns.builder.looslycouple;


public class Main {
    public static void main(String[] args) {

        Animal animal = new Animal
                .Builder()
                .setSpecies("Test")
                .setAge(12)
                .setWeight(23.3)
                .setName("Age").build();

        System.out.println(animal.toString());
    }
}
