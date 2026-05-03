package ocp.chapter2.example0.composition.interfaceway;

public class DatabaseStorage implements Storage {

    @Override
    public String store(String string) {
        return string + "saved to database";
    }
}
