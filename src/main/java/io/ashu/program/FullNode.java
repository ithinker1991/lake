package io.ashu.program;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;
import io.ashu.facade.wallet.ShellWallet;
import io.ashu.service.GenerateBlockService;
import io.ashu.service.PowGenerateBlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

@Slf4j
public class FullNode {
  public static void main(String[] args) throws InterruptedException {
    BlockChain blockChain = BlockChain.getInstance();
    GenerateBlockService generateBlockService = new PowGenerateBlockService(blockChain);
    generateBlockService.start();

    // start local wallet
    SpringApplication.run(ShellWallet.class, args);

//        blockChain.print();
//        Thread.sleep(100000);
//        generateBlockService.stop();
//        blockChain.print();

  }
}
