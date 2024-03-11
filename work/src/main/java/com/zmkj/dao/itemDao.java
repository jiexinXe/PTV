package com.example.ptv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface itemDao extends BaseMapper<item> {
}
