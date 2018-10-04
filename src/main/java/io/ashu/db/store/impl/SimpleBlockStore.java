package io.ashu.db.store.impl;

import io.ashu.core.model.Block;
import io.ashu.db.LevelDBSource;
import io.ashu.db.store.BlockStore;

public class SimpleBlockStore implements BlockStore {

  private LevelDBSource blockSource;
  private LevelDBSource blockIndexSource;

  public SimpleBlockStore() {
    blockSource = new LevelDBSource("block");
    blockIndexSource = new LevelDBSource("index");
  }


  @Override
  public void putBlock(Block block) {
    blockSource.put(block.getHash(), );
    blockIndexSource.put(block.getIndex(), block.getIndex());

  }

  @Override
  public Block getBlockByIndex(long index) {
    return null;
  }

  @Override
  public Block getBlock(byte[] hash) {
    return null;
  }
}
