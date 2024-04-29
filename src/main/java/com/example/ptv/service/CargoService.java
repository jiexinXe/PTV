package com.example.ptv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ptv.entity.Cargo;
import com.example.ptv.utils.PageUtils;
import com.example.ptv.utils.Rest;

import java.util.Map;

public interface CargoService extends IService<Cargo> {

    public boolean addCargo(Cargo cargo);
    public Rest deleteCargo(String cid, String num);
    public boolean updateCargo(Cargo cargo);
    public Cargo getCargoById(String cid);
    public Rest getCargoListByUserId(String userid);

    PageUtils queryPage(Map<String, Object> params);
}
