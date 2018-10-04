package io.ashu.db;

public interface Source<K, V> {

    void put(K k, V v);
    void delete(K k);
    V get(K k);

}
