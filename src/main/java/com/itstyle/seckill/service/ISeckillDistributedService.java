package com.itstyle.seckill.service;

import com.itstyle.seckill.common.entity.Result;

public interface ISeckillDistributedService {


	Result startSeckilRedisLock(long seckillId,long userId);

	Result startSeckilZksLock(long seckillId,long userId);
	

	Result startSeckilLock(long seckillId,long userId,long number);
	
}
