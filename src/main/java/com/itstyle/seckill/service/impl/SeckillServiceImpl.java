package com.itstyle.seckill.service.impl;

import com.itstyle.seckill.common.aop.Servicelock;
import com.itstyle.seckill.common.dynamicquery.DynamicQuery;
import com.itstyle.seckill.common.entity.Result;
import com.itstyle.seckill.common.entity.Seckill;
import com.itstyle.seckill.common.entity.SuccessKilled;
import com.itstyle.seckill.common.enums.SeckillStatEnum;
import com.itstyle.seckill.common.exception.RrException;
import com.itstyle.seckill.repository.SeckillRepository;
import com.itstyle.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Service("seckillService")
public class SeckillServiceImpl implements ISeckillService {
	private Lock lock = new ReentrantLock(true);
	
	@Autowired
	private DynamicQuery dynamicQuery;
	@Autowired
	private SeckillRepository seckillRepository;
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillRepository.findAll();
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillRepository.findOne(seckillId);
	}

	@Override
	public Long getSeckillCount(long seckillId) {
		String nativeSql = "SELECT count(*) FROM success_killed WHERE seckill_id=?";
		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
		return ((Number) object).longValue();
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteSeckill(long seckillId) {
		String nativeSql = "DELETE FROM  success_killed WHERE seckill_id=?";
		dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
		nativeSql = "UPDATE seckill SET number =100 WHERE seckill_id=?";
		dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result startSeckil(long seckillId,long userId) {
		//校验库存
		String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
		Long number =  ((Number) object).longValue();
		if(number>0){
			//扣库存
			nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
			dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
			//创建订单
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((short)0);
			killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dynamicQuery.save(killed);
            String table = "success_killed_"+userId%8;
            nativeSql = "INSERT INTO "+table+" (seckill_id, user_id,state,create_time)VALUES(?,?,?,?)";
            Object[] params = new Object[]{seckillId,userId,(short)0,new Timestamp(System.currentTimeMillis())};
            dynamicQuery.nativeExecuteUpdate(nativeSql,params);
			//支付
			return Result.ok(SeckillStatEnum.SUCCESS);
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result  startSeckilLock(long seckillId, long userId) {
		lock.lock();
		try {
			String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
			Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
			Long number =  ((Number) object).longValue();
			if(number>0){
				nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
				dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
				SuccessKilled killed = new SuccessKilled();
				killed.setSeckillId(seckillId);
				killed.setUserId(userId);
				killed.setState(Short.parseShort(number+""));
				killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
				dynamicQuery.save(killed);
			}else{
				return Result.error(SeckillStatEnum.END);
			}
		} catch (Exception e) {
			throw new RrException("异常了个乖乖");
         }finally {
			lock.unlock();
		}
		return Result.ok(SeckillStatEnum.SUCCESS);
	}
	@Override
	@Servicelock
	@Transactional(rollbackFor = Exception.class)
	public Result startSeckilAopLock(long seckillId, long userId) {
		//来自码云码友<马丁的早晨>的建议 使用AOP + 锁实现
		String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
		Long number =  ((Number) object).longValue();
		if(number>0){
			nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
			dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState(Short.parseShort(number+""));
			killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dynamicQuery.save(killed);
		}else{
			return Result.error(SeckillStatEnum.END);
		}
		return Result.ok(SeckillStatEnum.SUCCESS);
	}
	//注意这里 限流注解 可能会出现少买 自行调整
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result startSeckilDBPCC_ONE(long seckillId, long userId) {
		//单用户抢购一件商品或者多件都没有问题
		String nativeSql = "SELECT number FROM seckill WHERE seckill_id=? FOR UPDATE";
		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
		Long number =  ((Number) object).longValue();
		if(number>0){
			nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=?";
			dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((short)0);
			killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dynamicQuery.save(killed);
			return Result.ok(SeckillStatEnum.SUCCESS);
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result startSeckilDBPCC_TWO(long seckillId, long userId) {
		String nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=? AND number>0";
		int count = dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
		if(count>0){
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((short)0);
			killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dynamicQuery.save(killed);
			return Result.ok(SeckillStatEnum.SUCCESS);
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result startSeckilDBOCC(long seckillId, long userId, long number) {
		Seckill kill = seckillRepository.findOne(seckillId);
		if(kill.getNumber()>=number){
			//乐观锁
			String nativeSql = "UPDATE seckill  SET number=number-?,version=version+1 WHERE seckill_id=? AND version = ?";
			int count = dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{number,seckillId,kill.getVersion()});
			if(count>0){
				SuccessKilled killed = new SuccessKilled();
				killed.setSeckillId(seckillId);
				killed.setUserId(userId);
				killed.setState((short)0);
				killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
				dynamicQuery.save(killed);
				return Result.ok(SeckillStatEnum.SUCCESS);
			}else{
				return Result.error(SeckillStatEnum.END);
			}
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}

	@Override
	public Result startSeckilTemplate(long seckillId, long userId, long number) {
		return null;
	}

}
