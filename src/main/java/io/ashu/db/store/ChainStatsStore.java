package io.ashu.db.store;

import io.ashu.core.model.Block;

public interface ChainStatsStore {
  void put(byte[] key, byte[] val);
  void updateHeadBlockIndex(long index);
  long getHeadBlockIndex();
}
