package db;

public interface Source {
    void put(byte[] key, byte[] data);
    byte[] get(byte[] key);
}
