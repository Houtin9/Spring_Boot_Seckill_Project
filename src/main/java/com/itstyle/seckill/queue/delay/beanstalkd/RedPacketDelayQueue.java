package com.itstyle.seckill.queue.delay.beanstalkd;

import com.dinstone.beanstalkc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RedPacketDelayQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedPacketDelayQueue.class);

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.setServiceHost("58.87.107.182");
        config.setServicePort(11300);
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);
        JobProducer producer = factory.createJobProducer("redPacketDelayQueue");
        String msg = "你好, 微信红包";

        producer.putJob(100, 1, 5, msg.getBytes());
        JobConsumer consumer = factory.createJobConsumer("redPacketDelayQueue");
        while (true) {

            Job job = consumer.reserveJob(3);
            if (Objects.isNull(job)) {
                continue;
            }

            consumer.deleteJob(job.getId());

            LOGGER.info("任务ID：{}",job.getId());
            LOGGER.info("任务信息：{}",new String(job.getData()));
        }
    }
}


