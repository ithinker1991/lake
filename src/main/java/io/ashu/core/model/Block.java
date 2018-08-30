package io.ashu.core.model;

import java.util.Arrays;
import java.util.List;
import org.spongycastle.util.encoders.Hex;

public class Block {
    public Block(Head head, List<Transaction> transactions) {
        this.head = head;
        this.transactions = transactions;
    }

    public static class Head {
        private long index;
        private long timestamp;
        private byte[] hash;
        private byte[] parentHash;

        public Head(long index, long timestamp, byte[] hash, byte[] parentHash) {
            this.index = index;
            this.timestamp = timestamp;
            this.hash = hash;
            this.parentHash = parentHash;
        }

        @Override
        public String toString() {
            return "Head{" +
                    "index=" + index +
                    ", timestamp=" + timestamp +
                    ", hash=" + Hex.toHexString(hash) +
                    ", parentHash=" + Hex.toHexString(parentHash) +
                    '}';
        }
    }

    public byte[] getHash() {
        return head.hash;
    }
    // head
    private Head head;

    // data
    private List<Transaction> transactions;

    public static String computeHash(Block block) {
        return null;
    }

    @Override
    public String toString() {
        return "Block{" +
                "head=" + head +
                ", transactions=" + transactions +
                '}';
    }
}
