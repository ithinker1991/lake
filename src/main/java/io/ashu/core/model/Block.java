package io.ashu.core.model;

import com.alibaba.fastjson.JSON;
import io.ashu.core.model.transaction.GenesisTransaction;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.primitives.Longs;
import io.ashu.crypto.ByteUtil;
import io.ashu.crypto.HashUtil;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.spongycastle.util.encoders.Hex;

@Slf4j
public class Block {
    @Getter
    @Setter
    private long index;
    @Getter
    @Setter
    private long timestamp;
    @Setter
    private byte[] hash;
    @Setter
    @Getter
    private byte[] parentHash = new byte[0];
    @Getter
    private long nonce;

    private List<AbstractTransaction> transactions = new ArrayList<>();

    public void setNonce(long nonce) {
        this.nonce = nonce;
        dirty = true;
    }

    boolean dirty;

    private String blockId;

//    public Block(long index) {
//        this.index = index;
//        timestamp = System.currentTimeMillis();
//        transactions = new ArrayList<>();
//    }
//
//    public Block(long index, long timestamp, byte[] parentHash) {
//        this.index = index;
//        this.timestamp = timestamp;
//        this.parentHash = parentHash;
//    }

    private void computeHash() {
        byte[] indexBytes = Longs.toByteArray(index);
        byte[] tsBytes = Longs.toByteArray(timestamp);
        byte[] nonceBytes = Longs.toByteArray(nonce);
        byte[] blockBytes = ByteUtil.merge(indexBytes, tsBytes, parentHash, nonceBytes);

        hash = HashUtil.sha3(blockBytes);
    }



    public static String computeHash(Block block) {
        return null;
    }

    public byte[] getHash() {
        if (hash == null || dirty) {
            computeHash();
            dirty = false;
        }
        return hash;
    }

    public String getBlockId() {
        blockId = Hex.toHexString(getHash());
        return blockId;
    }

    public void addTransaction(AbstractTransaction trx) {
        transactions.add(trx);
    }

    public void addTransactions(Collection<AbstractTransaction> trxs) {
        transactions.addAll(trxs);
    }

    public void execute() {
        for (AbstractTransaction tx : transactions) {
            tx.validate();
            tx.execute();
        }
    }

    @Override
    public String toString() {
        return "Block{" +
            "index=" + index +
            ", timestamp=" + timestamp +
            ", hash=" + Hex.toHexString(getHash()) +
            ", parentHash=" + Hex.toHexString(parentHash) +
            ", transactions=" + transactions +
            ", nonce=" + nonce +
            '}';
    }

    public byte[] serialize() {
        String json = JSON.toJSONString(this);
        return json.getBytes();
    }

    public static Block deserialize(byte[] data) {
        try {
            return JSON.parseObject(new String(data, "UTF-8"), Block.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public static Block getGenesisBlock() {
        Block genesisBlock = new Block();
        genesisBlock.setIndex(0);
        genesisBlock.setNonce(100000);
        genesisBlock.setParentHash("parent_hash".getBytes());
        genesisBlock.setTimestamp(System.currentTimeMillis());

        GenesisTransaction transaction = new GenesisTransaction();

        Map<byte[], Long> genesisAccount = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            genesisAccount.put(("user" + 1).getBytes(), 10000000000L);
        }
        transaction.setGenesisAccounts(genesisAccount);

        genesisBlock.addTransaction(transaction);

        return genesisBlock;
    }
}
