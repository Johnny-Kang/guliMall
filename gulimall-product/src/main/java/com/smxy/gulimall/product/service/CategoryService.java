package com.smxy.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smxy.common.utils.PageUtils;
import com.smxy.gulimall.product.entity.CategoryEntity;

import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Johnny-Kang
 * @email Johnny-Kang@gmail.com
 * @date 2022-08-15 16:01:37
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

