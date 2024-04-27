package com.example.ptv.service.Imp;

import com.example.ptv.dao.CargoDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.service.CargoService;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CargoServiceImp implements CargoService {
        @Autowired
        private CargoDao cargoDao;

        public boolean addCargo(Cargo cargo) {
            int result = cargoDao.insert(cargo);
            return result > 0;
        }


}
