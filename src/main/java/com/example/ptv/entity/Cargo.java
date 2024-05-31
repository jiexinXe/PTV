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
 * 该实体类对应数据库中的数据货物表
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cargo")
public class Cargo {

    //货物ID
    @TableId(type = IdType.AUTO)
    Integer cid;
    //货物所在仓库的id
    String warehouseId;
    //货物名称
    String name;
    //货物种类
    Integer category;
    //货物数量
    Integer num;
    //货物单价
    BigDecimal price;
    //供应商
    String supplier;
    //入库时间
    Date enterTime;
    //入库位置
    String location;
//<<<<<<< .merge_file_UQWEf4
    Integer userid;

    //当前货物的状态
    //未审核：0 ，已审核：1 ， 已上架：2
    Integer status;
//=======
    //存放该货物的用户的id
//    Integer userid;
//
//>>>>>>> .merge_file_qji91F
}
