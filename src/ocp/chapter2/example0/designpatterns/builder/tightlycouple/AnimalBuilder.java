package ocp.chapter2.example0.designpatterns.builder.tightlycouple;

public class AnimalBuilder {
    private String species;
    private String name;
    private int age;
    private double weight;
    private String habitat;

    public AnimalBuilder setSpecies(String species){
        this.species = species;
        return this;
    }
    public AnimalBuilder setName(String name){
        this.name = name;
        return this;
    }
    public AnimalBuilder setAge(int age){
        this.age = age;
        return this;
    }
    public AnimalBuilder setWeight(double weight){
        this.weight = weight;
        return this;
    }
    public AnimalBuilder setHabitat(String habitat){
        this.habitat = habitat;
        return this;
    }

    public Animal Build(){
        return new Animal(species, name, age, weight);
    }
}
