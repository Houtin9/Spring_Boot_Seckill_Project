package com.itstyle.seckill.web;


import com.itstyle.seckill.common.entity.Result;
import com.itstyle.seckill.common.redis.RedisUtil;
import com.itstyle.seckill.common.utils.DoubleUtil;
import com.itstyle.seckill.queue.delay.jvm.RedPacketMessage;
import com.itstyle.seckill.queue.delay.jvm.RedPacketQueue;
import com.itstyle.seckill.service.IRedPacketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Api(tags ="抢红包")
@RestController
@RequestMapping("/redPacket")
public class RedPacketController {

	private final static Logger LOGGER = LoggerFactory.getLogger(RedPacketController.class);

	private static int corePoolSize = Runtime.getRuntime().availableProcessors();

	private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(1000));

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IRedPacketService redPacketService;


	@ApiOperation(value="抢红包一",nickname="爪哇笔记")
	@PostMapping("/start")
	public Result start(long redPacketId){
		int skillNum = 100;
		final CountDownLatch latch = new CountDownLatch(skillNum);

		redisUtil.cacheValue(redPacketId+"-num",10);

        redisUtil.cacheValue(redPacketId+"-restPeople",10);

		redisUtil.cacheValue(redPacketId+"-money",20000);

		for(int i=1;i<=skillNum;i++){
			int userId = i;
			Runnable task = () -> {

				long count = redisUtil.decr(redPacketId+"-num",1);
				if(count>=0){
					Result result = redPacketService.startSeckil(redPacketId,userId);
					Double amount = DoubleUtil.divide(Double.parseDouble(result.get("msg").toString()), (double) 100);
					LOGGER.info("用户{}抢红包成功，金额：{}", userId,amount);
				}else{
                    LOGGER.info("用户{}抢红包失败",userId);
                }
				latch.countDown();
			};
			executor.execute(task);
		}
		try {
			latch.await();
            Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
            LOGGER.info("剩余金额：{}",restMoney);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Result.ok();
	}

    @ApiOperation(value="抢红包二",nickname="爪哇笔记")
    @PostMapping("/startTwo")
    public Result startTwo(long redPacketId){
        int skillNum = 100;
        final CountDownLatch latch = new CountDownLatch(skillNum);

        redisUtil.cacheValue(redPacketId+"-num",10);

        redisUtil.cacheValue(redPacketId+"-money",20000);

        for(int i=1;i<=skillNum;i++){
            int userId = i;
            Runnable task = () -> {

                Integer money = (Integer) redisUtil.getValue(redPacketId+"-money");
                if(money>0){

                    Result result = redPacketService.startTwoSeckil(redPacketId,userId);
                    if(result.get("code").toString().equals("500")){
                        LOGGER.info("用户{}手慢了，红包派完了",userId);
                    }else{
                        Double amount = DoubleUtil.divide(Double.parseDouble(result.get("msg").toString()), (double) 100);
                        LOGGER.info("用户{}抢红包成功，金额：{}", userId,amount);
                    }
                }else{

                    //LOGGER.info("用户{}手慢了，红包派完了",userId);
                }
                latch.countDown();
            };
            executor.execute(task);
        }
        try {
            latch.await();
            Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
            LOGGER.info("剩余金额：{}",restMoney);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiOperation(value="抢红包三",nickname="爪哇笔记")
    @PostMapping("/startThree")
    public Result startThree(long redPacketId){
        int skillNum = 9;
        final CountDownLatch latch = new CountDownLatch(skillNum);

        redisUtil.cacheValue(redPacketId+"-num",10);

        redisUtil.cacheValue(redPacketId+"-money",20000);

        RedPacketMessage message = new RedPacketMessage(redPacketId,24);
        RedPacketQueue.getQueue().produce(message);

        for(int i=1;i<=skillNum;i++){
            int userId = i;
            Runnable task = () -> {

                Integer money = (Integer) redisUtil.getValue(redPacketId+"-money");
                if(money>0){
                    Result result = redPacketService.startTwoSeckil(redPacketId,userId);
                    if(result.get("code").toString().equals("500")){
                        LOGGER.info("用户{}手慢了，红包派完了",userId);
                    }else{
                        Double amount = DoubleUtil.divide(Double.parseDouble(result.get("msg").toString()), (double) 100);
                        LOGGER.info("用户{}抢红包成功，金额：{}", userId,amount);
                    }
                }
                latch.countDown();
            };
            executor.execute(task);
        }
        try {
            latch.await();
            Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
            LOGGER.info("剩余金额：{}",restMoney);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
}