package io.ashu.db.store.impl;

import com.google.common.primitives.Longs;
import io.ashu.db.LevelDBSource;
import io.ashu.db.store.ChainStatsStore;

public class SimpleChainStatsStore implements ChainStatsStore {

  private LevelDBSource chainStatsSource;

  private static final byte[] HEAD_BLOCK_INDEX= "head_block_index".getBytes();

  public SimpleChainStatsStore() {
    chainStatsSource = new LevelDBSource("chain-stats");
  }

  @Override
  public void put(byte[] key, byte[] val) {
    chainStatsSource.put(key, val);
  }

  @Override
  public void updateHeadBlockIndex(long index) {
    chainStatsSource.put(HEAD_BLOCK_INDEX, Longs.toByteArray(index));
  }

  @Override
  public long getHeadBlockIndex() {
    byte[] data = chainStatsSource.get(HEAD_BLOCK_INDEX);
    if (data == null) {
      return -1;
    }
    return Longs.fromByteArray(data);
  }
}
