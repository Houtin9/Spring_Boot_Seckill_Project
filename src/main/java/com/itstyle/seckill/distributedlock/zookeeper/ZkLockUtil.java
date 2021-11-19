package com.itstyle.seckill.distributedlock.zookeeper;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkLockUtil{
	
	private static String address = "192.168.1.180:2181";
	
	public static CuratorFramework client;
	
	static{
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3); 
        client = CuratorFrameworkFactory.newClient(address, retryPolicy); 
        client.start();
	}

    private ZkLockUtil(){};

    private static class SingletonHolder{

    	private  static InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock"); 
    }
    public static InterProcessMutex getMutex(){
        return SingletonHolder.mutex;
    }
    //获得了锁
    public static boolean acquire(long time, TimeUnit unit){
    	try {
			return getMutex().acquire(time,unit);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    //释放锁
    public static void release(){
    	try {
			getMutex().release();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}  
