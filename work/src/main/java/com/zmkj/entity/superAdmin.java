package com.example.ptv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该实体类对应数据库中的超级管理员实体
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("superAdmin")
public class superAdmin {
    Integer id;
    String role;
    String name;
    String info;
}
