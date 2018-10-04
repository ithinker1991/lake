package io.ashu.service;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;
import lombok.Getter;

public abstract class AbstractGenerateBlockService extends GenerateBlockService {
    private volatile boolean shouldExit = false;
    private Thread generateThread;

    @Getter
    private BlockChain blockChain;

    public AbstractGenerateBlockService(BlockChain blockChain) {
        this.blockChain = blockChain;
        generateThread = new Thread(new GenerateLoop());
        generateThread.setName("generate-block-thread");
    }

    private class GenerateLoop implements Runnable {

        @Override
        public void run() {
            while(!shouldExit) {
                if (canGenerate()) {
                    Block block = generateBlock();
                    blockChain.pushBlock(block);
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void start() {
        shouldExit = false;
        generateThread.start();
    }

    @Override
    public void stop() {
        shouldExit = true;
    }


}
