package com.example.ptv.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ptv.entity.orderInfo;
import com.example.ptv.entity.order_use;
import com.example.ptv.entity.orders;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ordersDao extends BaseMapper<orders> {
    @Insert("INSERT INTO order_info (item_id, num_of_item, start_time, end_time, user_id) VALUES(#{itemId}, #{numOfItem}, #{startTime}, #{endTime}, #{userId})")
    void addOrderInfo(@Param("itemId")Integer itemId, @Param("numOfItem")Double numOfItem, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("userId")Integer userId);

    @Insert("INSERT INTO order_info (item_id, num_of_item, start_time, end_time, user_id) VALUES(#{itemId}, #{numOfItem}, #{startTime}, #{endTime}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "oinfo_id")
    void autoAddOrderInfo(orderInfo orderInfo);
    @Select("Select oinfo_id from order_info where user_id = #{userId}")
    List<Integer> selectIdByuserId(@Param("userId") Integer userId);

    @Insert("INSERT INTO orders (oinfo_id, type, states) VALUES (#{id}, #{type}, '已完成')")
    void addOrder(@Param("id")Integer id, @Param("type")String type);
    @Insert("INSERT INTO orders (oinfo_id, states) VALUES (#{oinfoId},  '待审批')")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void autoAddOrder(orders orders);

    @Select("select DISTINCT id, states, name, type ,num_of_Item, start_time, end_time from orders, order_info, cargo where user_id = #{userid} and orders.oinfo_id = order_info.oinfo_id and cargo.cid = order_info.item_id")
    List<order_use> getOrdersByUserid(@Param("userid")String userid);
}
