package com.example.ptv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该实体类对应数据库中的货物实体
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("item")
public class item {
    Integer id;//货物id
    String name;//货物名称
    String type;//货物类型
    String info;//其他货物信息
}
