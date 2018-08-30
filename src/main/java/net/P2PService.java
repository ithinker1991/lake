package net;

import io.ashu.core.model.Block;
import io.ashu.service.INetService;

public class P2PService implements INetService {
    private int port;

    @Override
    public Block reciveBlock() {
        return null;
    }

    @Override
    public boolean sendBlock() {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
