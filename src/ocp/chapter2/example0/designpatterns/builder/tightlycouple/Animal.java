package ocp.chapter2.example0.designpatterns.builder.tightlycouple;

public class Animal {
    private String species;
    private String name;
    private int age;
    private double weight;

    public  Animal(String species, String name, int age, double weight) {
        this.species = species;
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public String getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "species='" + species + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }
}
