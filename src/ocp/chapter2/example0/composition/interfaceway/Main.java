package ocp.chapter2.example0.composition.interfaceway;

public class Main {

    public static void main(String[] args) {
        DataManager dataManager1 = new DataManager(new DatabaseStorage());
        System.out.println(dataManager1.store("Hello World"));
        DataManager dataManager2 = new DataManager(new FileStorage());
        System.out.println(dataManager2.store("Hello World2"));
    }

}
