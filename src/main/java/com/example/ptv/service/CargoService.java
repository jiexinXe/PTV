package com.example.ptv.service;

import com.example.ptv.entity.Cargo;
import com.example.ptv.utils.Rest;

public interface CargoService {

    public boolean addCargo(Cargo cargo, String userid);
    public Rest deleteCargo(String cid, String num);
    public boolean updateCargo(Cargo cargo);
    public Cargo getCargoById(String cid);
    public Rest getCargoListByUserId(String userid);
}
