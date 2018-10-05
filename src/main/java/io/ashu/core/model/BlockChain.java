package io.ashu.core.model;

import io.ashu.db.store.BlockStore;
import io.ashu.db.store.ChainStatsStore;
import io.ashu.db.store.impl.SimpleBlockStore;
import io.ashu.db.store.impl.SimpleChainStatsStore;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class BlockChain implements Serializable {
    private Block genesisBlock;
    private BlockStore blockStore;
    private ChainStatsStore statsStore;
    @Getter
    private List<Transaction> pendingTransactions;
    private long headIndex = -1;

    public BlockChain(Block genesisBlock) {
//        blocks = new ArrayList<>();
        blockStore = new SimpleBlockStore();
        statsStore = new SimpleChainStatsStore();
        pendingTransactions = new LinkedList<>();

        if (statsStore.getHeadBlockIndex() > 1) {

        } else {
          pushBlock(genesisBlock);
        }
    }

    public Block getHeadBlock() {
        long index = statsStore.getHeadBlockIndex();
        return blockStore.getBlockByIndex(index);
    }

    public long getSize() {
        return statsStore.getHeadBlockIndex();
    }

    public synchronized void pushBlock(Block block) {
        blockStore.putBlock(block);
        statsStore.updateHeadBlockIndex(block.getIndex());
        System.out.println("Push block: " + block);
    }

    public long getHeadIndex() {
      return statsStore.getHeadBlockIndex();
    }

    public void print() {
        System.out.println("----------- block ------------");
        for (int i = 0; i <= statsStore.getHeadBlockIndex(); i++) {
            System.out.println(blockStore.getBlockByIndex(i));

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
//      blocks.add(block);
      blockStore.putBlock(block);
    }

    public void clearPendingTransactions() {
        this.pendingTransactions.clear();
    }

}
