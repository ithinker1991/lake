package io.ashu.service;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockChainService  implements  IService{
  private GenerateBlockService generateBlockService;
  private BlockChain blockChain;
  private volatile boolean shouldExit;

  public BlockChainService(GenerateBlockService generateBlockService, BlockChain blockChain){
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
    log.error("Generage Block !!!");
    new Thread(() -> {
      while(!shouldExit) {

        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Block block = generateBlockService.generateBlock();
        blockChain.pushBlock(block);
      }
    }).start();

  }

  @Override
  public void stop() {
    shouldExit = false;
  }
}
