package io.ashu.core.model;

import io.ashu.db.store.AccountStore;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTransaction {
    public static enum Type {
        TRANSFER,
    }

    @Autowired
    private AccountStore accountStore;

    @Getter
    private byte[] ownerAddress;
    private int type;

    @Getter
    private Account owner;

    private byte[] data;
    private byte[] signature;

    public AbstractTransaction(byte[] ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public AbstractTransaction() {
//        owner = accountStore.getAccount(ownerAddress);
    }

    public byte[] asBytes() {
        return data;
    }

    public abstract void validate();
    public abstract void execute();
}
