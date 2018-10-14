package io.ashu.core.model;

import lombok.Getter;

public abstract class Transaction {
    public static enum Type {
        TRANSFER,
    }

    @Getter
    private byte[] ownerAddress;
    private int type;


    private byte[] data;
    private byte[] signature;

    public Transaction(byte[] ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public Transaction() {
    }

    public byte[] asBytes() {
        return data;
    }

    public abstract void validate();
    public abstract void excute();
}
