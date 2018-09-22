package io.ashu.core.model;


import io.ashu.db.HashMapSource;
import io.ashu.db.Source;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlockChain {
    private Block genesisBlock;
    private Source blockStore;
    @Getter
    private List<Transaction> pendingTransactions;
    private long headIndex = -1;

    public BlockChain(Source blockStore) {
        this.blockStore = blockStore;
        this.blocks = new ArrayList<>();
        headIndex = 0;
    }

    public BlockChain() {
        blocks = new ArrayList<>();
        pendingTransactions = new LinkedList<>();
    }

    private List<Block> blocks;

    public Block getHeadBlock() {
        return blocks.get(getSize() - 1);
    }

    public int getSize() {
        return blocks.size();
    }

    public synchronized void pushBlock(Block block) {
        blocks.add(block);
//        blockStore.put(block.getHash(), block.getHash());
        System.out.println("Push block: " + block);
    }

    public long getHeadIndex() {
//        return this.headIndex;
        return blocks.size() - 1;
    }

    public void print() {
        System.out.println("----------- block ------------");
        for (int i = 0; i < blocks.size(); i++) {
            System.out.println(blocks.get(i));
        }
        System.out.println("-----------------------------");
    }

    public void clearPendingTransactions() {
        this.pendingTransactions.clear();
    }
}
