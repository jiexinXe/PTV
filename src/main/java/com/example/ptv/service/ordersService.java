package com.example.ptv.service;

import com.example.ptv.utils.Rest;

import java.util.Date;

public interface ordersService {

    public Rest addOrder(String user_id, String name, String itemtype, Date start_time, Date end_time);
}
