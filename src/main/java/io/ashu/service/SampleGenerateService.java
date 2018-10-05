package io.ashu.service;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;

public class SampleGenerateService extends AbstractGenerateBlockService {


    public SampleGenerateService(BlockChain blockChain) {
        super(blockChain);
    }

    @Override
    public Block generateBlock() {
        long headIndex = getBlockChain().getHeadIndex();
        Block headBlock = getBlockChain().getHeadBlock();
        return createSequencedBlock(headIndex + 1);
    }

    private Block createSequencedBlock(long index) {
        Block block = new Block();
        block.setIndex(index);
        return block;
    }



    @Override
    public boolean canGenerate() {
        return false;
    }
}
