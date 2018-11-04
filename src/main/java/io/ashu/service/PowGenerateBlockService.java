package io.ashu.service;

import com.google.common.base.Strings;
import io.ashu.core.model.Transaction;
import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;
import lombok.Setter;

import java.util.List;

public class PowGenerateBlockService extends AbstractGenerateBlockService {
    @Setter
    private int difficulty;

    public PowGenerateBlockService(BlockChain blockChain) {
        super(blockChain);
        difficulty = 5;
    }

    private String getHashPrefix() {
        return Strings.repeat("0", difficulty);
    }

    @Override
    public Block generateBlock() {
        long headIndex = getBlockChain().getHeadIndex();
        Block parentBlock = getBlockChain().getHeadBlock();
        List<Transaction> transactionList = getBlockChain().getPendingTransactions();

        Block block = new Block();
        block.setIndex(headIndex + 1);
        block.addTransactions(transactionList);
        block.setParentHash(parentBlock.getHash());
        block.setTimestamp(System.currentTimeMillis());

        for (long nonce = 0; nonce < Long.MAX_VALUE; nonce++) {
            block.setNonce(nonce);
            if (matchRule(block)) {
                break;
            }
        }
        return block;
    }

    @Override
    public boolean canGenerate() {
        return true;
    }

    private boolean matchRule(Block block) {
        return block.getBlockId().startsWith(getHashPrefix());
    }
}
