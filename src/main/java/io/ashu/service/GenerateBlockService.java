package io.ashu.service;

import io.ashu.core.model.Block;

public abstract class GenerateBlockService implements IService {
    public abstract Block generateBlock();

    public abstract boolean canGenerate();
}
