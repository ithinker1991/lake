package io.ashu.service;

import com.google.common.base.Strings;
import com.sun.deploy.util.StringUtils;
import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;
import io.ashu.core.model.Transaction;

import java.math.BigInteger;
import java.util.List;

public class PowGenerateBlockService extends AbstractGenerateBlockService {
    private int difficity;
    private String hashPrefix;
    public PowGenerateBlockService(BlockChain blockChain) {
        super(blockChain);
        difficity = 4;
    }

    public String getHashPrefix() {
        hashPrefix = Strings.repeat("0", difficity);
        return hashPrefix;
    }

    @Override
    public Block generateBlock() {
        long headIndex = getBlockChain().getHeadIndex();
        List<Transaction> transactionList = getBlockChain().getPendingTransactions();

        Block block = new Block(headIndex + 1);
        block.addTransactions(transactionList);

        for (long nonce = 0; nonce < Long.MAX_VALUE; nonce++) {
            block.setNonce(nonce);
            if (satficgenerate(block)) {
                break;
            }
        }

        getBlockChain().clearPendingTransactions();

        return block;
    }

    @Override
    public boolean canGenerate() {
        return true;
    }

    public boolean satficgenerate(Block block) {

//        System.out.println(new BigInteger(1, block.getHash()).toString(2));
//        System.out.println( new BigInteger(1, block.getHash()).toString(2));
        return block.getBlockId().startsWith(getHashPrefix());
//        return new BigInteger(1, block.getHash()).toString(2).substring(1).startsWith(getHashPrefix());
    }

}
