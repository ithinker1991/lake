package io.ashu.core.model;

import static io.ashu.core.model.Block.getGenesisBlock;

import io.ashu.db.store.BlockStore;
import io.ashu.db.store.ChainStatsStore;
import io.ashu.db.store.impl.SimpleBlockStore;
import io.ashu.db.store.impl.SimpleChainStatsStore;
import java.io.Serializable;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BlockChain implements Serializable {
  private Block genesisBlock;
  private BlockStore blockStore;
  private ChainStatsStore statsStore;

  private static BlockChain blockChain;
  static {
    try {
      blockChain = new BlockChain(getGenesisBlock());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static BlockChain getInstance() {
    return blockChain;
  }


  @Getter
  private List<Transaction> pendingTransactions;
  private long headIndex = -1;

  private BlockChain(Block genesisBlock) {
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

  public synchronized void pushTransaction(Transaction tx) {
    pendingTransactions.add(tx);
  }

  public synchronized void pushBlock(Block block) {
    vailditeBlock(block);
    applyBlock(block);
  }

  private void vailditeBlock(Block block) {

  }

  private void applyBlock(Block block) {
    block.execute();
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
