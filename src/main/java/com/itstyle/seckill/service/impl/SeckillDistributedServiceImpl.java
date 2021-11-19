package com.itstyle.seckill.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.itstyle.seckill.common.exception.RrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itstyle.seckill.common.dynamicquery.DynamicQuery;
import com.itstyle.seckill.common.entity.Result;
import com.itstyle.seckill.common.entity.SuccessKilled;
import com.itstyle.seckill.common.enums.SeckillStatEnum;
import com.itstyle.seckill.distributedlock.redis.RedissLockUtil;
import com.itstyle.seckill.distributedlock.zookeeper.ZkLockUtil;
import com.itstyle.seckill.service.ISeckillDistributedService;
@Service
public class SeckillDistributedServiceImpl implements ISeckillDistributedService {
	
	@Autowired
	private DynamicQuery dynamicQuery;
	
	@Override
	@Transactional
	public Result startSeckilRedisLock(long seckillId,long userId) {
		boolean res=false;
		try {
			res = RedissLockUtil.tryLock(seckillId+"", TimeUnit.SECONDS, 3, 20);
			if(res){
				String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
				Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
				Long number =  ((Number) object).longValue();
				if(number>0){
					SuccessKilled killed = new SuccessKilled();
					killed.setSeckillId(seckillId);
					killed.setUserId(userId);
					killed.setState((short)0);
					killed.setCreateTime(new Timestamp(new Date().getTime()));
					dynamicQuery.save(killed);
					nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=? AND number>0";
					dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
				}else{
					return Result.error(SeckillStatEnum.END);
				}
			}else{
			    return Result.error(SeckillStatEnum.MUCH);
			}
		} catch (Exception e) {
            throw new RrException("异常");
		} finally{
			if(res){//释放锁
				RedissLockUtil.unlock(seckillId+"");
			}
		}
		return Result.ok(SeckillStatEnum.SUCCESS);
	}
	@Override
	@Transactional
	public Result startSeckilZksLock(long seckillId, long userId) {
		boolean res=false;
		try {
			res = ZkLockUtil.acquire(3,TimeUnit.SECONDS);
			if(res){
				String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
				Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
				Long number =  ((Number) object).longValue();
				if(number>0){
					SuccessKilled killed = new SuccessKilled();
					killed.setSeckillId(seckillId);
					killed.setUserId(userId);
					killed.setState((short)0);
					killed.setCreateTime(new Timestamp(new Date().getTime()));
					dynamicQuery.save(killed);
					nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=? AND number>0";
					dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
				}else{
					return Result.error(SeckillStatEnum.END);
				}
			}else{
			    return Result.error(SeckillStatEnum.MUCH);
			}
		} catch (Exception e) {
            throw new RrException("异常了个乖乖");
		} finally{
			if(res){//释放锁
				ZkLockUtil.release();
			}
		}
		return Result.ok(SeckillStatEnum.SUCCESS);
	}

	@Override
	@Transactional
	public Result startSeckilLock(long seckillId, long userId, long number) {
		boolean res=false;
		try {
			res = RedissLockUtil.tryLock(seckillId+"", TimeUnit.SECONDS, 3, 10);
			if(res){
				String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
				Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
				Long count =  ((Number) object).longValue();
				if(count>=number){
					SuccessKilled killed = new SuccessKilled();
					killed.setSeckillId(seckillId);
					killed.setUserId(userId);
					killed.setState((short)0);
					killed.setCreateTime(new Timestamp(new Date().getTime()));
					dynamicQuery.save(killed);
					nativeSql = "UPDATE seckill  SET number=number-? WHERE seckill_id=? AND number>0";
					dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{number,seckillId});
				}else{
					return Result.error(SeckillStatEnum.END);
				}
			}else{
				return Result.error(SeckillStatEnum.MUCH);
			}
		} catch (Exception e) {
            throw new RrException("异常");
		} finally{
			if(res){//释放锁
				RedissLockUtil.unlock(seckillId+"");
			}
		}
		return Result.ok(SeckillStatEnum.SUCCESS);
	}

}
