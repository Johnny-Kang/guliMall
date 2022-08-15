package com.smxy.gulimall.order.dao;

import com.smxy.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Johnny-Kang
 * @email Johnny-Kang@gmail.com
 * @date 2022-08-15 17:18:50
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
