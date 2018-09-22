package io.ashu.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.primitives.Longs;
import io.ashu.crypto.ByteUtil;
import io.ashu.crypto.HashUtil;
import lombok.Getter;
import lombok.Setter;
import org.spongycastle.util.encoders.Hex;


public class Block {
    private long index;
    private long timestamp;
    private byte[] hash;
    private byte[] parentHash = new byte[0];
    private long nonce;

    public void setNonce(long nonce) {
        this.nonce = nonce;
        dirty = true;
    }

    boolean dirty;

    private String blockId;

    public Block(long index) {
        this.index = index;
        timestamp = System.currentTimeMillis();
        transactions = new ArrayList<>();
    }

    private void computeHash() {
        byte[] indexBytes = Longs.toByteArray(index);
        byte[] tsBytes = Longs.toByteArray(timestamp);
        byte[] nonceBytes = Longs.toByteArray(nonce);
        byte[] blockBytes = ByteUtil.merge(indexBytes, tsBytes, parentHash, nonceBytes);

        hash = HashUtil.sha3(blockBytes);
    }

    // data
    private List<Transaction> transactions;

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

    public void addTransaction(Transaction trx) {
        transactions.add(trx);
    }

    public void addTransactions(Collection<Transaction> trxs) {
        transactions.addAll(trxs);
    }


    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", hash=" + Hex.toHexString(hash) +
                ", parentHash=" + Hex.toHexString(parentHash) +
                ", transactions=" + transactions +
                ", nonce=" + nonce +
                '}';
    }
}
