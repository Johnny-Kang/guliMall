package com.smxy.gulimall.coupon.dao;

import com.smxy.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author Johnny-Kang
 * @email Johnny-Kang@gmail.com
 * @date 2022-08-15 16:53:29
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
