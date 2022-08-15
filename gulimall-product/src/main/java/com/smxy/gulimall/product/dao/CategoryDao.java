package com.smxy.gulimall.product.dao;

import com.smxy.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Johnny-Kang
 * @email Johnny-Kang@gmail.com
 * @date 2022-08-15 10:55:46
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
