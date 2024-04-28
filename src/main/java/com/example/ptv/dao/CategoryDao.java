package com.example.ptv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.Category;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryDao extends BaseMapper<Category> {
}
