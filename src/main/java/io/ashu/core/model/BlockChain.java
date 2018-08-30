package io.ashu.core.model;


import java.util.ArrayList;
import java.util.List;

public class BlockChain {

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

        System.out.println("Push block: " + block);
    }


}
