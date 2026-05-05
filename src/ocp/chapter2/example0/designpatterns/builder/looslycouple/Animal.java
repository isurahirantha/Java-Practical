package ocp.chapter2.example0.designpatterns.builder.looslycouple;

public class Animal {
    private final String species;
    private final String name;
    private final int age;
    private final double weight;

    public  Animal(Builder builder) {
        this.species = builder.species;
        this.name = builder.name;
        this.age = builder.age;
        this.weight = builder.weight;
    }

    public static class Builder{
        private String species;
        private String name;
        private int age;
        private double weight;

        public Builder setSpecies(String species){
            this.species = species;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setAge(int age){
            this.age = age;
            return this;
        }
        public Builder setWeight(double weight){
            this.weight = weight;
            return this;
        }

        public Animal build() {
            return new Animal(this);  // Coupled but controlled
        }
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
