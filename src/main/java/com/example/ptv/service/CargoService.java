package com.example.ptv.service;

import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.utils.Rest;

import java.util.List;

public interface CargoService {

    public boolean addCargo(Cargo cargo, String userid);
    public Rest deleteCargo(String warehouse_id, String shelve_id, String row, String column);
    public boolean updateCargo(Cargo cargo);
    public Cargo getCargoById(String cid);
    public Rest getCargoListByUserId(String userid);
    public String getShelveIdOfCargo(String cargoid);
    public Rest getLocationInfo(String[] locations);
    public Rest readyToRemove(String username, String cid, String num);
}
