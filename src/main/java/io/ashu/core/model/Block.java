package io.ashu.core.model;

import java.util.List;

import com.google.common.primitives.Longs;
import io.ashu.crypto.ByteUtil;
import io.ashu.crypto.HashUtil;
import org.spongycastle.util.encoders.Hex;


public class Block {
    private long index;
    private long timestamp;
    private byte[] hash;
    private byte[] parentHash = new byte[0];

    public Block(long index) {
        this.index = index;
        timestamp = System.currentTimeMillis();
        hash = computeHash();
    }

    private byte[] computeHash() {
        byte[] indexBytes = Longs.toByteArray(index);
        byte[] tsBytes = Longs.toByteArray(timestamp);
        byte[] blockBytes = ByteUtil.merge(indexBytes, tsBytes, parentHash);

        return HashUtil.sha3(blockBytes);
    }

    // data
    private List<Transaction> transactions;

    public static String computeHash(Block block) {
        return null;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", hash=" + Hex.toHexString(hash) +
                ", parentHash=" + Hex.toHexString(parentHash) +
                ", transactions=" + transactions +
                '}';
    }
}
