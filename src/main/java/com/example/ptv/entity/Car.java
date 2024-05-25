package com.example.ptv.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 该实体类对应数据库中的小车表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("car")
public class Car {

    //ID
    @TableId(type = IdType.AUTO)
    Integer id;
    //当前状态
    Integer status;
    //当前位置
    String location;
    //当前任务
    String currentTask;

}
