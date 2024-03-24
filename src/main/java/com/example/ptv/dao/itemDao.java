package com.example.ptv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface itemDao extends BaseMapper<item> {

    @Insert("INSERT INTO item (name, type, user_id, end_time) VALUES (#{name}, #{type}, #{user_id}, #{end_time})")
    void addItem(@Param("name")String name, @Param("type")String type, @Param("user_id")Integer user_id, @Param("end_time")Date end_time);

    @Select("SELECT item_id FROM `item` where user_id = #{userId}")
    List<Integer> selectItemidByUserid(@Param("userId")Integer userId);

}
