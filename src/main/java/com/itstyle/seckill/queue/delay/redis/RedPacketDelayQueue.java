package com.itstyle.seckill.queue.delay.redis;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

public class RedPacketDelayQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedPacketDelayQueue.class);

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                                .setPassword("123456").setDatabase(2);
        RedissonClient redissonClient = Redisson.create(config);

        RBlockingQueue<RedPacketMessage> blockingRedPacketQueue
                = redissonClient.getBlockingQueue("redPacketDelayQueue");
        RDelayedQueue<RedPacketMessage> delayedRedPacketQueue
                = redissonClient.getDelayedQueue(blockingRedPacketQueue);

        delayedRedPacketQueue.offer(new RedPacketMessage(20200113), 3, TimeUnit.SECONDS);
        delayedRedPacketQueue.offer(new RedPacketMessage(20200114), 5, TimeUnit.SECONDS);
        delayedRedPacketQueue.offer(new RedPacketMessage(20200115), 10, TimeUnit.SECONDS);

        while (true){

            RedPacketMessage redPacket = blockingRedPacketQueue.take();
            LOGGER.info("红包ID:{}过期失效",redPacket.getRedPacketId());
        }
    }
}


