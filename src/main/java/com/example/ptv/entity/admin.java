package com.example.ptv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该实体类对应数据库中的仓库管理员实体
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("admin")
public class admin {
    Integer id;
    String role;
    String name;
    String info;
}
