package com.itstyle.seckill.distributedlock.redis;

import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RedissLockUtil {
    private static RedissonClient redissonClient;
    
    public void setRedissonClient(RedissonClient locker) {
    	redissonClient = locker;
    }
    

    public static RLock lock(String lockKey) {
    	RLock lock = redissonClient.getLock(lockKey);
    	lock.lock();
        return lock;
    }


    public static void unlock(String lockKey) {
    	RLock lock = redissonClient.getLock(lockKey);
		lock.unlock();
    }
    

    public static void unlock(RLock lock) {
    	lock.unlock();
    }


    public static RLock lock(String lockKey, int timeout) {
    	RLock lock = redissonClient.getLock(lockKey);
		lock.lock(timeout, TimeUnit.SECONDS);
		return lock;
    }
    

    public static RLock lock(String lockKey, TimeUnit unit ,int timeout) {
    	RLock lock = redissonClient.getLock(lockKey);
		lock.lock(timeout, unit);
		return lock;
    }
    

    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
		try {
			return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			return false;
		}
    }
    

    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
    	RLock lock = redissonClient.getLock(lockKey);
		try {
			return lock.tryLock(waitTime, leaseTime, unit);
		} catch (InterruptedException e) {
			return false;
		}
    }


    public void initCount(String key,int count) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
        mapCache.putIfAbsent(key,count,3,TimeUnit.DAYS);
    }

    public int incr(String key, int delta) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return  mapCache.addAndGet(key, 1);//加1并获取计算后的值
    }


    public int decr(String key, int delta) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return mapCache.addAndGet(key, -delta);//加1并获取计算后的值
    }
}