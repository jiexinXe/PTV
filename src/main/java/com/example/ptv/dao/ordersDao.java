package com.example.ptv.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface ordersDao {

    @Insert("INSERT INTO orders (user_id, name, item_type, start_time, end_time)VALUES (#{user_id}, #{oname}, #{type}, #{start_time}, #{end_time})")
    void addOrder(@Param("user_id") String user_id, @Param("oname") String oname,@Param("type") String type,@Param("start_time") Date start_time,@Param("end_time") Date end_time);
}
