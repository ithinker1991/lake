package io.ashu.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.fusesource.leveldbjni.JniDBFactory;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

public class LevelDBSource implements Source<byte[], byte[]> {

  private String name;
  private DB db;
  private boolean alive;

  private ReadWriteLock resetDBLock = new ReentrantReadWriteLock();

  public LevelDBSource(String name) {
    this.name = name;
    init();
  }

//  public

  public void init() {
    // step1: 获取读锁
    resetDBLock.writeLock().lock();

    try {
      // db 引擎设置
      Options options = new Options();
      options.cacheSize(0);

      // db 目录配置
      Path dbPath = getPath();
      if (!Files.isSymbolicLink(dbPath.getParent())) {
        Files.createDirectories(dbPath.getParent());
      }

      try {
        db = JniDBFactory.factory.open(dbPath.toFile(), options);
      } catch (IOException e) {
        //
        throw e;
      }

      alive = true;
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      resetDBLock.writeLock().unlock();
    }
  }

  private Path getPath() {
    // TODO base dir to config
    return Paths.get("db", name);
  }


  @Override
  public void put(byte[] key, byte[] val) {
    resetDBLock.readLock().lock();
    try{
      db.put(key, val);
    } finally {
      resetDBLock.readLock().unlock();
    }
  }

  @Override
  public void delete(byte[] key) {
    resetDBLock.readLock().lock();
    try {
      db.delete(key);
    } finally {
      resetDBLock.readLock().unlock();
    }

  }

  @Override
  public byte[] get(byte[] key) {
    resetDBLock.readLock().lock();
    try {
      return db.get(key);
    }finally {
      resetDBLock.readLock().unlock();
    }
  }

  public void close() {
    resetDBLock.writeLock().lock();
    try {
      if (!alive) {
        return;
      }
      db.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      resetDBLock.writeLock().unlock();
    }
  }


}
