package com.itstyle.seckill.service;

import java.util.List;

import com.itstyle.seckill.common.entity.Result;
import com.itstyle.seckill.common.entity.Seckill;

public interface ISeckillService {


	List<Seckill> getSeckillList();


	Seckill getById(long seckillId);

	Long getSeckillCount(long seckillId);

	void deleteSeckill(long seckillId);
	

	Result startSeckil(long seckillId,long userId);
	

	Result startSeckilLock(long seckillId,long userId);

	Result startSeckilAopLock(long seckillId,long userId);
	

	Result startSeckilDBPCC_ONE(long seckillId,long userId);

	Result startSeckilDBPCC_TWO(long seckillId,long userId);

	Result startSeckilDBOCC(long seckillId,long userId,long number);
	

	Result startSeckilTemplate(long seckillId,long userId,long number);
    
}
