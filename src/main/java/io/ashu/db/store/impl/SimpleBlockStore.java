package io.ashu.db.store.impl;


import com.google.common.primitives.Longs;
import io.ashu.core.model.Block;
import io.ashu.db.LevelDBSource;
import io.ashu.db.store.BlockStore;
import org.springframework.stereotype.Component;

@Component
public class SimpleBlockStore implements BlockStore {

  private LevelDBSource blockSource;
  private LevelDBSource blockIndexSource;

  public SimpleBlockStore() {
    blockSource = new LevelDBSource("block");
    blockIndexSource = new LevelDBSource("block-index");
  }


  @Override
  public void putBlock(Block block) {
    blockSource.put(block.getHash(), block.serialize());
    blockIndexSource.put(Longs.toByteArray(block.getIndex()), block.getHash());
  }

  @Override
  public Block getBlockByIndex(long index) {
    byte[] hash = blockIndexSource.get(Longs.toByteArray(index));
    return getBlock(hash);
  }

  @Override
  public Block getBlock(byte[] hash) {
    return Block.deserialize(blockSource.get(hash));
  }
}
