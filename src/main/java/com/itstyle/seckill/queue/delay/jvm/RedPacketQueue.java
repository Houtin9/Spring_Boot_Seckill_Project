package com.itstyle.seckill.queue.delay.jvm;

import java.util.concurrent.DelayQueue;

public class RedPacketQueue {

    private static DelayQueue<RedPacketMessage> queue = new DelayQueue<>();

    private RedPacketQueue(){}

    private static class SingletonHolder{

        private  static RedPacketQueue queue = new RedPacketQueue();
    }
    //单例队列
    public static RedPacketQueue getQueue(){
        return SingletonHolder.queue;
    }

    public  Boolean  produce(RedPacketMessage message){
        return queue.add(message);
    }

    public  RedPacketMessage consume() throws InterruptedException {
        return queue.take();
    }
}
