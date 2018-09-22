package io.ashu.core.model;

public class Transaction {
    private int type;

    public byte[] asBytes() {
        return data;
    }
    private byte[] data;

    public Transaction(String msg) {
        this.data = msg.getBytes();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                '}';
    }
}
