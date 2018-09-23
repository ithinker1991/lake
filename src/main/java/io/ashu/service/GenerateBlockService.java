package io.ashu.service;

import io.ashu.core.model.Block;
import io.ashu.core.model.BlockChain;

public abstract class GenerateBlockService implements IService {

    public abstract Block generateBlock();

    public abstract boolean canGenerate();
}
