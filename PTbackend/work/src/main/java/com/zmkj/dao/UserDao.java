package com.zmkj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmkj.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
}
