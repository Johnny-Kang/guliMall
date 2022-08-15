package com.smxy.gulimall.member.dao;

import com.smxy.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Johnny-Kang
 * @email Johnny-Kang@gmail.com
 * @date 2022-08-15 17:10:30
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
