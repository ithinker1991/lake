package io.ashu.core.model;

import com.alibaba.fastjson.JSON;
import io.ashu.db.store.AccountStore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTransaction {
    public static enum Type {
        TRANSFER,
    }

    @Autowired
    private AccountStore accountStore;

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

    public AbstractTransaction(byte[] ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public AbstractTransaction() {
//        owner = accountStore.getAccount(ownerAddress);
    }

    public byte[] serialize() {
        String json = JSON.toJSONString(this);
        return json.getBytes();
    }

    public abstract void validate();
    public abstract void execute();
}
