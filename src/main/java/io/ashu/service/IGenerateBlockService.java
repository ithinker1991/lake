package io.ashu.service;

import io.ashu.core.model.Block;

public interface IGenerateBlockService extends IService {
    Block generateBlock();

    boolean canGenerate();



}
