package io.ashu.service;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;

import java.util.concurrent.atomic.AtomicBoolean;

public class BlockChainService  implements  IService{
    private IGenerateBlockService generateBlockService;
    private BlockChain blockChain;
    private volatile boolean shouldExit;

    public BlockChainService(IGenerateBlockService generateBlockService, BlockChain blockChain){
        this.generateBlockService = generateBlockService;
        this.blockChain = blockChain;
        shouldExit = false;
    }


    @Override
    public void init() {
        return;
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!shouldExit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Block block = generateBlockService.generateBlock();
                    blockChain.pushBlock(block);
                }
            }
        }).start();

    }

    @Override
    public void stop() {
        shouldExit = false;
    }
}
