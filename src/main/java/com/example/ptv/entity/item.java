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
    Integer id;
    String name;
    String type;
    String info;
}
