package io.ashu;

import io.ashu.core.model.BlockChain;
import io.ashu.service.BlockChainService;
import io.ashu.service.GenerateBlockService;
import io.ashu.service.SampleGenerateService;

public class Main {

    public static void main(String[] args) {
        // 定时产块即为共识
        GenerateBlockService generateBlockService = new SampleGenerateService();

        // model 即 DB
        BlockChain blockChain = new BlockChain();


        BlockChainService bs = new BlockChainService(generateBlockService, blockChain);
        bs.start();


        // 启动钱包服务
    }
}
