package io.ashu.service;

import io.ashu.core.model.Block;
import org.spongycastle.util.encoders.Hex;

public class SampleGenerateService implements IGenerateBlockService {
    public SampleGenerateService() {
        firstBlockHash = "ashu".getBytes();

    }

    private long headIndex;
    private byte[] firstBlockHash;

    @Override
    public Block generateBlock() {
        Block.Head head = newHead();
        return new Block(head, null);
    }

    private Block.Head newHead() {
        return new Block.Head(headIndex++, System.currentTimeMillis(), firstBlockHash, firstBlockHash);
    }

    @Override
    public boolean canGenerate() {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
