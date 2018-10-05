package io.ashu.program;

import ch.qos.logback.classic.Level;
import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;
import io.ashu.service.GenerateBlockService;
import io.ashu.service.PowGenerateBlockService;
import io.ashu.service.SampleGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class FullNode {

    private final static Logger logger = LoggerFactory.getLogger(FullNode.class);

    public static void main(String[] args) throws InterruptedException {
//        Logger root =  LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//        root.setLevel(Level);

        logger.warn("fsdfsdf");

        BlockChain blockChain = new BlockChain(Block.getGenesisBlock());
        GenerateBlockService generateBlockService = new PowGenerateBlockService(blockChain);

        generateBlockService.start();
        blockChain.print();
        Thread.sleep(100000);
        generateBlockService.stop();
        blockChain.print();
    }
}
