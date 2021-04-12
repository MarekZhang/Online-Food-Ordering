package com.imooc.takeaway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("RedisLock")
public class RedisLock {
  @Autowired
  StringRedisTemplate stringRedisTemplate;

  /**
   * lock
   * @param key  productId
   * @param value currentTime + timeout
   * @return
   */
  public boolean lock(String key, String value) {
    //current thread get the lock
    if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
      return true;
    }

    //check if the lock is expired
    String currentValue = stringRedisTemplate.opsForValue().get(key);
    //lock is expired
    if (currentValue != null && currentValue.length() != 0 && Long.parseLong(currentValue) < System.currentTimeMillis()) {
      String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
      //current thread get the lock
      if (oldValue!=null && oldValue.length() != 0 && currentValue.equals(oldValue))
        return true;
    }

    return false;
  }

  /**
   * unlock
   * @param key productId
   */
  public void unlock(String key, String value) {
    String oldValue = stringRedisTemplate.opsForValue().get(key);
    if (oldValue != null && oldValue.length() != 0 && oldValue.equals(value)) {
      stringRedisTemplate.delete(key);
    }
  }
}
