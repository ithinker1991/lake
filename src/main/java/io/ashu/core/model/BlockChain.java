package io.ashu.core.model;


import db.HashMapSource;
import db.Source;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    Source blockStore = new HashMapSource();

    public BlockChain() {
        blocks = new ArrayList<>();
    }
    private List<Block> blocks;

    public Block getHeadBlock() {
        return blocks.get(getSize() - 1);
    }


    public int getSize() {
        return blocks.size();
    }



    public void pushBlock(Block block) {
        blocks.add(block);
        blockStore.put(block.getHash(), block.getHash());

        System.out.println("Push block: " + block);
    }


}
