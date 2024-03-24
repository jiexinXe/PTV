package com.example.ptv.service;

import com.example.ptv.utils.Rest;

import java.util.Date;

public interface ordersService {

    public Rest addOrder(String itemName, Double numOfItem, String type, Integer userId, Date startTime, Date endTime);
}
