package com.itstyle.seckill.service;

import com.itstyle.seckill.common.entity.RedPacket;
import com.itstyle.seckill.common.entity.Result;

public interface IRedPacketService {


    RedPacket get(long redPacketId);

	Result startSeckil(long redPacketId,int userId);


    Result startTwoSeckil(long redPacketId,int userId);

}
