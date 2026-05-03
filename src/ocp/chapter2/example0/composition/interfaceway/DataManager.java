package ocp.chapter2.example0.composition.interfaceway;

public class DataManager {
    private Storage storage;
    public DataManager(Storage storage) {
        this.storage = storage;
    }

    String store(String string) {
        return storage.store(string);
    }
}
