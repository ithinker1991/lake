package io.ashu.core.model;

import com.alibaba.fastjson.JSON;
import io.ashu.db.store.AccountStore;
import io.ashu.db.store.impl.SimpleAccountStore;
import io.ashu.db.store.impl.SimpleBlockStore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Transaction {
    public static enum Type {
        TRANSFER,
    }

    private AccountStore accountStore = SimpleAccountStore.getInstance();

    @Getter
    private byte[] ownerAddress;
    @Getter
    @Setter
    private long timestamp;
    private int type;

    @Getter
    private Account owner;

    private byte[] data;
    private byte[] signature;

    public Transaction(byte[] ownerAddress) {
        this.ownerAddress = ownerAddress;
        owner = accountStore.getAccount(ownerAddress);
    }

    public byte[] serialize() {
        String json = JSON.toJSONString(this);
        return json.getBytes();
    }

    public abstract void validate();
    public abstract void execute();
}
