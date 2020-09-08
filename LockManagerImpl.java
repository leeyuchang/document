package com.jpa1.jpa1.service;

import static java.time.Duration.ofMinutes;
import static java.util.stream.Collectors.toList;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class LockManagerImpl implements LockManager {

  private List<LockData> lockDataList = new ArrayList<>();
  private final long lockTimeout = 5; // Time Unit : Min

  @Getter
  @ToString
  @EqualsAndHashCode
  @AllArgsConstructor
  public static class LockData {

    private String type;
    private String id;
    private LockId lockId;
    private LocalTime expirationTime;

    public boolean isExpired() {
      return expirationTime.isAfter(LocalTime.now());
    }
  }

  @Override
  public LockId tryLock(String type, String id) throws LockException {
    checkAlreadyLocked(type, id);
    LockId lockId = new LockId(UUID.randomUUID().toString());
    locking(type, id, lockId);
    return lockId;
  }

  private void checkAlreadyLocked(String type, String id) {
    Predicate<LockData> idAndType = e -> e.getType().equals(type) && e.getId().equals(id);
    List<LockData> locks = lockDataList.stream().filter(idAndType).collect(toList());
    Optional<LockData> lockData = handleExpiration(locks);

    if (lockData.isPresent()) throw new AlreadyLockedException("AlreadyLockedException");
  }

  private void locking(String type, String id, LockId lockId) {
    lockDataList.add(new LockData(type, id, lockId, LocalTime.now()));
  }

  private Optional<LockData> handleExpiration(List<LockData> locks) {
    if (locks.isEmpty()) return Optional.empty();
    LockData lockData = locks.get(0);
    if (lockData.isExpired()) {
      lockDataList.remove(lockData);
      return Optional.empty();
    } else {
      return Optional.of(lockData);
    }
  }

  private LocalTime getExpirationTime() {
    return LocalTime.now().plus(ofMinutes(lockTimeout));
  }

  @Override
  public void checkLock(LockId lockId) throws LockException {
    Optional<LockData> lockData = getLockData(lockId);
    if (lockData.isEmpty())
      throw new NoLockException("NoLockException");
  }

  private Optional<LockData> getLockData(LockId lockId) {
    return lockDataList.stream().filter(e -> e.getLockId().equals(lockId)).findFirst();
  }

  @Override
  public void extendLockExpiration(LockId lockId) throws LockException {
    LockData lockData = findLockDataByLockId(lockId);
    lockDataList.remove(lockData);
    lockDataList.add(new LockData(lockData.getType(), lockData.getId(), lockData.getLockId(), getExpirationTime()));
  }

  @Override
  public void releaseLock(LockId lockId) throws LockException {
    LockData lockdata = findLockDataByLockId(lockId);
    lockDataList.remove(lockdata);
  }

  private LockData findLockDataByLockId(LockId lockId) {
    Optional<LockData> lockDataOpt = getLockData(lockId);
    LockData lockData = lockDataOpt.orElseThrow(() -> new NoLockException("NoLockException"));
    return lockData;
  }

  public List<LockData> getLockDataList() {
    return Collections.unmodifiableList(lockDataList);
  }

}
