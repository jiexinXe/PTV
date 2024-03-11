package com.example.ptv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该实体类对应货物存放在仓库中的位置
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@TableName("location_item")
public class location {

    Integer id;
    Integer itemId;
    Integer WHId;
    Integer location;
}
