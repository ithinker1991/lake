package io.ashu.service;

import io.ashu.core.model.Block;

public interface INetService extends IService {
    Block reciveBlock();
    boolean sendBlock();
}
