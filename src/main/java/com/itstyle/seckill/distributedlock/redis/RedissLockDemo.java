package com.itstyle.seckill.distributedlock.redis;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;


public class RedissLockDemo {

	public void testReentrantLock(RedissonClient redisson) {
		RLock lock = redisson.getLock("anyLock");
		try {
			// 1. 最常见的使用方法
			// lock.lock();
			// 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
			// lock.lock(10, TimeUnit.SECONDS);
			// 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
			boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
			if (res) { // 成功
				// do your business
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void testAsyncReentrantLock(RedissonClient redisson) {
		RLock lock = redisson.getLock("anyLock");
		try {
			lock.lockAsync();
			lock.lockAsync(10, TimeUnit.SECONDS);
			Future<Boolean> res = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);
			if (res.get()) {
				// do your business
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void testFairLock(RedissonClient redisson){  
	    RLock fairLock = redisson.getFairLock("anyLock");  
	    try{  
	        // 最常见的使用方法  
	        fairLock.lock();  
	        // 支持过期解锁功能, 10秒钟以后自动解锁,无需调用unlock方法手动解锁  
	        fairLock.lock(10, TimeUnit.SECONDS);  
	        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁  
	        boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);  
	        if (res) {
				// do your business
			}
	    } catch (InterruptedException e) {  
	        e.printStackTrace();  
	    } finally {  
	        fairLock.unlock();  
	    }  
//      Redisson同时还为分布式可重入公平锁提供了异步执行的相关方法：
//	    RLock fairLock = redisson.getFairLock("anyLock");  
//	    fairLock.lockAsync();  
//	    fairLock.lockAsync(10, TimeUnit.SECONDS);  
//	    Future<Boolean> res = fairLock.tryLockAsync(100, 10, TimeUnit.SECONDS);  
	} 

	public void testMultiLock(RedissonClient redisson1,RedissonClient redisson2, RedissonClient redisson3){  
	    RLock lock1 = redisson1.getLock("lock1");  
	    RLock lock2 = redisson2.getLock("lock2");  
	    RLock lock3 = redisson3.getLock("lock3");  
	    RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);  
	    try {  
	        // 同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。  
	        lock.lock();  
	        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁  
	        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);  
	        if (res) {
				// do your business
			}
	    } catch (InterruptedException e) {  
	        e.printStackTrace();  
	    } finally {  
	        lock.unlock();  
	    }  
	}  

	public void testRedLock(RedissonClient redisson1,RedissonClient redisson2, RedissonClient redisson3){  
	    RLock lock1 = redisson1.getLock("lock1");  
	    RLock lock2 = redisson2.getLock("lock2");  
	    RLock lock3 = redisson3.getLock("lock3");  
	    RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);  
	    try {  
	        // 同时加锁：lock1 lock2 lock3, 红锁在大部分节点上加锁成功就算成功。  
	        lock.lock();  
	        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁  
	        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);  
	        if (res) {
				// do your business
			}
	    } catch (InterruptedException e) {  
	        e.printStackTrace();  
	    } finally {  
	        lock.unlock();  
	    }  
	}  
	//读写锁（ReadWriteLock）、信号量（Semaphore）、可过期性信号量（PermitExpirableSemaphore）、闭锁（CountDownLatch）
}