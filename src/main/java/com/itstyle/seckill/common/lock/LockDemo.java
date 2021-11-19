package com.itstyle.seckill.common.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class LockDemo {
	
	private static Lock lock = new ReentrantLock();
	
	private static int num1 = 0;
	private static int num2 = 0;
	public static void main(String[] args) {
		lockDemo();
		SyncDemo();
	}

	public static void lockDemo(){
		long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			final int num = i;
			new Runnable() {
				@Override
				public void run() {
					lock(num);
				}
			}.run();
		}
		long end = System.currentTimeMillis();
		System.out.println("累加："+num1);
		System.out.println("ReentrantLock锁："+ (end-start));
	}
	public static void SyncDemo(){
		long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			final int num = i;
			new Runnable() {
				@Override
				public void run() {
					sync(num);
				}
			}.run();
		}
		long end = System.currentTimeMillis();
		System.out.println("累加："+num2);
		System.out.println("synchronized锁："+ (end-start));
	}
    public static void lock(int i){
    	lock.lock();
    	num1 ++;
    	lock.unlock();
    }
    public static synchronized void sync(int i){
    	num2 ++;
    }
}
