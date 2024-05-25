package com.example.ptv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.Car;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CarDao extends BaseMapper<Car> {
}
