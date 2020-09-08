package com.jpa1.jpa1.service;

public interface LockManager {
  
  LockId tryLock(String type, String id) throws LockException;

  void checkLock(LockId lockId) throws LockException;

  void releaseLock(LockId lockId) throws LockException;

  void extendLockExpiration(LockId lockId) throws LockException;

}
