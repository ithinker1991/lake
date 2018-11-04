package io.ashu.core.model;

import com.alibaba.fastjson.JSON;
import io.ashu.core.model.transaction.GenesisTransaction;
import io.ashu.crypto.ECKey;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

    private List<Transaction> transactions = new ArrayList<>();

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

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
    }

    public void addTransactions(Collection<Transaction> txs) {
        transactions.addAll(txs);
    }

    public void execute() {

        Iterator<Transaction> iterator = transactions.iterator();
        while (iterator.hasNext()) {
            Transaction tx = iterator.next();
            tx.execute();
            tx.validate();
            iterator.remove();
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


    public static Block getGenesisBlock() throws Exception {
        Block genesisBlock = new Block();
        genesisBlock.setIndex(0);
        genesisBlock.setNonce(100000);
        genesisBlock.setParentHash("parent_hash".getBytes());
        genesisBlock.setTimestamp(System.currentTimeMillis());



        GenesisTransaction transaction = new GenesisTransaction("generis".getBytes());

        Map<byte[], Long> genesisAccount = new HashMap<>();
        String[] privateKeys = {
            "78233cbac07903a790165b80694d8e2631ea833a2101a7fff728281870509eeb",
            "e35e7b3ad49767c6f0cc007a8b1e384d2f42f34b54af07ed4d644f9eb4b9ed5a",
            "216420084ed106a85f94caf368b4e7168d9424f89177b0ab753bd7ffab81b64f",
            "d9241159faee87a7c4ce5aef14d0ca44853434e0929a2e49e92273c225513cf4",
            "130398ccec618b883c5598e6d1e16f7c9910038f912e78a4f4b5e923e1e647b1",
            "6feaf8ecb4655090c9aef25b4212ff2cf0ad0e16517a1ee83d5096e853987890",
            "6dbac2ade8f98a98bff7fe9fda7c3c870f0a34d8abd23fe168b06c38fb89ec64",
            "772d27c6bfb18aefd235a6d61aefba5c507cef379124edb245d5e1af559f532e",
            "c5f2f7538f0a5d20962bc88c6384ed785380b3f406779f1552601b73feb0ef92",
            "3a779c4aab222c9afc82685e8e7547bf8b3f0bb36872286b57496a5d9850ba84",
        };

        for (int i = 0; i < 10; i++) {
//            ECKey ecKey = ECKey.newECKey();
            ECKey ecKey = ECKey.fromprivateKey(Hex.decode(privateKeys[i]));
            System.err.println(Hex.toHexString(ecKey.getAddress()) + "  " + privateKeys[i]);
            genesisAccount.put(ecKey.getAddress(), 10000000000L);
        }
        transaction.setGenesisAccounts(genesisAccount);

        genesisBlock.addTransaction(transaction);

        return genesisBlock;
    }
}
