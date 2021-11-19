package com.itstyle.seckill.queue.kafka;

import com.itstyle.seckill.common.enums.SeckillStatEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.itstyle.seckill.common.entity.Result;
import com.itstyle.seckill.common.redis.RedisUtil;
import com.itstyle.seckill.common.webSocket.WebSocketServer;
import com.itstyle.seckill.service.ISeckillService;

@Component
public class KafkaConsumer {
	@Autowired
	private ISeckillService seckillService;
	
	private static RedisUtil redisUtil = new RedisUtil();
    @KafkaListener(topics = {"seckill"})
    public void receiveMessage(String message){

		String[] array = message.split(";");
    	if(redisUtil.getValue(array[0])==null){
    		Result result = seckillService.startSeckilAopLock(Long.parseLong(array[0]), Long.parseLong(array[1]));
			if(result.equals(Result.ok(SeckillStatEnum.SUCCESS))){
    			WebSocketServer.sendInfo(array[0], "秒杀成功");
    		}else{
    			WebSocketServer.sendInfo(array[0], "秒杀失败");
    			redisUtil.cacheValue(array[0], "ok");
    		}
    	}else{
    		WebSocketServer.sendInfo(array[0], "秒杀失败");
    	}
    }
}