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
        return null;
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
