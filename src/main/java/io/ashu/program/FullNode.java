package io.ashu.program;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;

public class FullNode {

    public static void main(String[] args) {
        BlockChain blockChain = new BlockChain();
        blockChain.pushBlock(new Block(blockChain.getHeadIndex() + 1));
        blockChain.pushBlock(new Block(blockChain.getHeadIndex() + 1));
        blockChain.pushBlock(new Block(blockChain.getHeadIndex() + 1));
        blockChain.print();
    }
}
