package io.ashu.db.store;

import io.ashu.core.model.Block;

public interface BlockStore {
  void putBlock(Block block);
  Block getBlockByIndex(long index);
  Block getBlock(byte[] hash);
}
