package com.example.ptv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class order_use {
    String id;
    String states;//订单状态，包括（进行中，完成，中止）
    String type;
    String name;//货物name，从货物表中获取货物信息
    Integer numOfItem;//该订单中货物的数量
    Date startTime;//存放开始时间
    Date endTime;//存放结束时间
}
