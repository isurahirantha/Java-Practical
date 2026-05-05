package ocp.chapter2.example0.designpatterns.builder.tightlycouple;


public class Main {
    public static void main(String[] args) {
        Animal animal = new AnimalBuilder()
                .setSpecies("Test")
                .setAge(12)
                .setWeight(23.3)
                .setName("Age")
                .Build();
        System.out.println(animal.toString());
    }
}
