package io.ashu.db;

public interface Source {
    void put(byte[] key, byte[] data);
    void delete(byte[] key);
    byte[] get(byte[] key);

}
