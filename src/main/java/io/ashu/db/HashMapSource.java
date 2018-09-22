package io.ashu.db;

import java.util.HashMap;
import java.util.Map;

public class HashMapSource implements Source{

    private Map<byte[], byte[]> map;

    public HashMapSource() {
        map = new HashMap<>();
    }

    @Override
    public void put(byte[] key, byte[] data) {
        map.put(key,data);

    }

    @Override
    public void delete(byte[] key) {

    }

    @Override
    public byte[] get(byte[] key) {
        return map.get(key);
    }
}
