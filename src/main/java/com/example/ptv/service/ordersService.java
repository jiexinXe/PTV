package com.example.ptv.service;

import com.example.ptv.entity.orders;
import com.example.ptv.utils.Rest;

import java.util.Date;
import java.util.List;

public interface ordersService {

    public Rest addOrder(String itemName, Double numOfItem, String type, Integer userId, Date startTime, Date endTime);
    void autoAddOrder(Integer cargoId);
    public Rest getOrdersByuserid(String userid);
    public Rest approve(String oid);
}
