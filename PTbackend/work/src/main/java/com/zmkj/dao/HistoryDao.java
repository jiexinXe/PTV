package com.zmkj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmkj.entity.HistoryEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryDao extends BaseMapper<HistoryEntity> {
}
