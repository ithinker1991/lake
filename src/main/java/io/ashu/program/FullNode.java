package io.ashu.program;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;
import io.ashu.service.GenerateBlockService;
import io.ashu.service.PowGenerateBlockService;

public class FullNode {

    public static void main(String[] args) throws InterruptedException {
        BlockChain blockChain = new BlockChain();
//        blockChain.pushBlock(new Block(blockChain.getHeadIndex() + 1));
//        blockChain.pushBlock(new Block(blockChain.getHeadIndex() + 1));
//        blockChain.pushBlock(new Block(blockChain.getHeadIndex() + 1));
        GenerateBlockService generateBlockService = new PowGenerateBlockService(blockChain);
        generateBlockService.start();
        blockChain.print();
        Thread.sleep(100000);
        generateBlockService.stop();
        blockChain.print();
    }
}
