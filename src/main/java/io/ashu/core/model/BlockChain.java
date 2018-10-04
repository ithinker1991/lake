package io.ashu.core.model;

import io.ashu.db.LevelDBSource;
import io.ashu.db.Source;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlockChain implements Serializable {
    private Block genesisBlock;
    private Source blockStore;
    @Getter
    private List<Transaction> pendingTransactions;
    private long headIndex = -1;

    public BlockChain(Source blockStore) {
        this.blockStore = blockStore;
        this.blocks = new ArrayList<>();
        headIndex = 0;

    }

    public BlockChain(Block genesisBlock) {
        blocks = new ArrayList<>();
        blockStore = new LevelDBSource("block");
        pendingTransactions = new LinkedList<>();
        pushBlock(genesisBlock);
    }

    private List<Block> blocks;

    public Block getHeadBlock() {
        return blocks.get(getSize() - 1);
    }

    public int getSize() {
        return blocks.size();
    }

    public synchronized void pushBlock(Block block) {
        blocks.add(block);
//        blockStore.put(block.getHash(), block.);
        System.out.println("Push block: " + block);
    }

    public long getHeadIndex() {
//        return this.headIndex;
        return blocks.size() - 1;
    }

    public void print() {
        System.out.println("----------- block ------------");
        for (int i = 0; i < blocks.size(); i++) {
            System.out.println(blocks.get(i));

        }
        System.out.println("-----------------------------");
    }

    private void put(Block block) throws IOException {
      ObjectOutputStream oos = null;
      ByteArrayOutputStream bos = null;

      byte[] data = null;
      try {
        bos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(bos);
        oos.writeObject(block);
        data = bos.toByteArray();
      } finally {
        bos.close();
        oos.close();
      }
      blocks.add(block);
      blockStore.put(block.getHash(), data);
    }

    public void clearPendingTransactions() {
        this.pendingTransactions.clear();
    }

}
