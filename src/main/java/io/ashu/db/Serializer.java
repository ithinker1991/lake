package io.ashu.db;

public interface Serializer<T> {
  byte[] serializer(T t);
  T deserialize(byte[] bytes);
}
