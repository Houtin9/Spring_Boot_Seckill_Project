package com.itstyle.seckill.queue.delay.netty;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RedPacketTimerTask implements TimerTask {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final long redPacketId;


    private final long timestamp;

    public RedPacketTimerTask(long redPacketId) {
        this.redPacketId = redPacketId;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public void run(Timeout timeout) {
        System.out.println(String.format("任务执行时间:%s,红包创建时间:%s,红包ID:%s",
                LocalDateTime.now().format(F), LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).format(F), redPacketId));
    }
}
