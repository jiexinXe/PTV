package com.example.ptv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.warehouse;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface warehouseDao extends BaseMapper<warehouse> {
}
