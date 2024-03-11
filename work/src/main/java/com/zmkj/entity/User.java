package com.example.ptv.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 该实体类对应数据库中的用户数据表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("user")
//因为还没有连接数据库因此无法使用该注释
public class User {
    Integer id;
    String name;
    String role;
    String info;
}
