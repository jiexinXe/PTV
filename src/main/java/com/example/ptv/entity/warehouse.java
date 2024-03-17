package com.example.ptv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该实体类对应数据库中的仓库实体
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("warehouse")
public class warehouse {

    Integer id;
    String type;
    Double maxContent;
    Double nowContent;
}
