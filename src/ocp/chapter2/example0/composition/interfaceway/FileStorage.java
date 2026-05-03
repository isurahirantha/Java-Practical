package ocp.chapter2.example0.composition.interfaceway;

public class FileStorage implements Storage {
    @Override
    public String store(String string) {
        return string + "saved to file storage";
    }
}
