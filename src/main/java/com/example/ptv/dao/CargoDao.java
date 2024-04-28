package com.example.ptv.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.Cargo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CargoDao extends BaseMapper<Cargo> {

    @Select("SELECT * FROM `cargo` where user_id = #{userId}")
    List<Integer> selectCargoByUserid(@Param("userId")Integer userId);
}
