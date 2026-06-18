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

    // With STATIC inner class - WORKS
    // Animal.Builder builder = new Animal.Builder();  // ✅ No Animal needed
    // If it were NON-STATIC (instance inner class) - WOULDN'T WORK!
    // Animal.Builder builder = new Animal.Builder(); // ❌ Compilation error
    // You would need: Animal animal = new Animal();
}
