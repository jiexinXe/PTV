package com.example.ptv.service.Imp;

import com.example.ptv.dao.CarDao;
import com.example.ptv.entity.Car;
import com.example.ptv.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImp implements CarService {

    @Autowired
    private CarDao carDao;
    @Override
    public List<Car> getAllCars() {
        return carDao.selectList(null); // 获取所有车辆信息
    }

}
