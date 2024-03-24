package com.example.ptv.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ordersDao {
    @Insert("INSERT INTO order_info (item_id, num_of_item, start_time, end_time, user_id) VALUES(#{itemId}, #{numOfItem}, #{startTime}, #{endTime}, #{userId})")
    void addOrderInfo(@Param("itemId")Integer itemId, @Param("numOfItem")Double numOfItem, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("userId")Integer userId);

    @Select("Select oinfo_id from order_info where user_id = #{userId}")
    List<Integer> selectIdByuserId(@Param("userId") Integer userId);

    @Insert("INSERT INTO orders (oinfo_id, type, states) VALUES (#{id}, #{type}, '已完成')")
    void addOrder(@Param("id")Integer id, @Param("type")String type);
}
