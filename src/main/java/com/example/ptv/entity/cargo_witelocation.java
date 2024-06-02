package com.example.ptv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class cargo_witelocation {
    //货物ID
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
    //货物的货架位置
    String shelve_location;

    public  cargo_witelocation (Cargo cargo){
        this.cid = cargo.getCid();
        this.category = cargo.getCategory();
        this.num = cargo.getNum();
        this.warehouseId = cargo.getWarehouseId();
        this.name = cargo.getName();
        this.price = cargo.getPrice();
        this.supplier = cargo.getSupplier();
        this.enterTime = cargo.getEnterTime();
        this.location = cargo.getLocation();
        this.userid = cargo.getUserid();
        this.status = cargo.getStatus();
    }
}
