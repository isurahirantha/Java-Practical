package ocp.chapter7.synchronizers.phaser.BankTransactionApp.model;

public class Transaction {
    private final String id;
    private final double amount;
    private boolean valid;

    public Transaction(String id, double amount) {
        this.id = id;
        this.amount = amount;
        this.valid = false;
    }

    public String getId() { return id; }
    public double getAmount() { return amount; }
    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    @Override
    public String toString() {
        return id + "," + amount + "," + (valid ? "VALID" : "INVALID");
    }
}
