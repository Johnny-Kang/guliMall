package com.smxy.gulimall.ware.dao;

import com.smxy.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author Johnny-Kang
 * @email Johnny-Kang@gmail.com
 * @date 2022-08-15 17:22:43
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
