package com.example.ptv.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


/**
 * author:sh
 * 实体说明:这个实体存放了订单的货物信息方面，也可以认为是订单详细信息
 *
 * */


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_info")
public class order_info {
    Integer id;//订单货物信息的id，用于订单表获取信息
    Integer itemId;//货物id，从货物表中获取货物信息
    Integer numOfItem;//该订单中货物的数量
    Date startTime;//存放开始时间
    Date endTime;//存放结束时间
}
