package com.itstyle.seckill.queue.jvm;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.itstyle.seckill.common.entity.SuccessKilled;

public class SeckillQueue {
	 //队列大小
    static final int QUEUE_MAX_SIZE   = 100;

    static BlockingQueue<SuccessKilled> blockingQueue = new LinkedBlockingQueue<SuccessKilled>(QUEUE_MAX_SIZE);
    

    private SeckillQueue(){};

    private static class SingletonHolder{

		private  static SeckillQueue queue = new SeckillQueue();
    }


    public static SeckillQueue getSkillQueue(){
        return SingletonHolder.queue;
    }

    public  Boolean  produce(SuccessKilled kill) {
    	return blockingQueue.offer(kill);
    }
    public  SuccessKilled consume() throws InterruptedException {
        return blockingQueue.take();
    }

    public int size() {
        return blockingQueue.size();
    }
}
