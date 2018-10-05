package io.ashu.core.model;

import io.ashu.db.store.BlockStore;
import io.ashu.db.store.ChainStatsStore;
import io.ashu.db.store.impl.SimpleBlockStore;
import io.ashu.db.store.impl.SimpleChainStatsStore;
import java.io.Serializable;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockChain implements Serializable {
    private Block genesisBlock;
    private BlockStore blockStore;
    private ChainStatsStore statsStore;
    @Getter
    private List<Transaction> pendingTransactions;
    private long headIndex = -1;

    public BlockChain(Block genesisBlock) {
        blockStore = new SimpleBlockStore();
        statsStore = new SimpleChainStatsStore();
        pendingTransactions = new LinkedList<>();

        if (statsStore.getHeadBlockIndex() <= -1) {
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

        log.info("Push block: " + block);

    }

    public long getHeadIndex() {
      return statsStore.getHeadBlockIndex();
    }

    public void print() {

      log.info("----------- block ------------");
        for (long i = 0; i <= statsStore.getHeadBlockIndex(); i++) {
          log.info(blockStore.getBlockByIndex(i).toString());
        }
        log.info("-----------------------------");
    }

    public void clearPendingTransactions() {
        this.pendingTransactions.clear();
    }

}
