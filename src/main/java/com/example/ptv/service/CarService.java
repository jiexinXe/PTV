package com.example.ptv.service;

import com.example.ptv.entity.Car;
import com.example.ptv.utils.Rest;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();

    public void processOrder(Integer orderId);
    public Rest searchLocationInShelves(String order_id);
    public void processCargo(String cid, String sid);
}
