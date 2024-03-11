package com.zmkj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmkj.entity.UserItemEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserItemDao extends BaseMapper<UserItemEntity> {
}
