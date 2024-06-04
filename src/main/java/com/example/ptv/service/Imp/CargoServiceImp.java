package com.example.ptv.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.ShelvesDao;
import com.example.ptv.dao.UserDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.ShelvesEntity;
import com.example.ptv.entity.User;
import com.example.ptv.entity.cargo_witelocation;
import com.example.ptv.service.CargoService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.ibatis.jdbc.Null;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CargoServiceImp implements CargoService {
        @Autowired
        private CargoDao cargoDao;
        @Autowired
        private ShelvesDao shelvesdao;
        @Autowired
        private KafkaTemplate<String ,Object> kafkaTemplate;
        @Autowired
        private Gson gson = new GsonBuilder().create();
        @Autowired
        private UserDao userdao;

        @Autowired
        private CarServiceImp carserviceimp;

    public boolean addCargo(Cargo cargo, String userid) {
        cargo.setStatus(0);

        // 创建查询包装器来查找货物添加者
        QueryWrapper<User> adderWrapper = new QueryWrapper<>();
        adderWrapper.eq("id", userid);
        User cargoAdder = userdao.selectOne(adderWrapper);

        // 创建查询包装器来查找货物所属用户
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("id", cargo.getUserid());
        User cargoUser = userdao.selectOne(userWrapper);

        // 检查cargoAdder和cargoUser是否相同，如果不同且cargoAdder的角色为3，则返回false
        if (!Objects.equals(cargoAdder, cargoUser)) {
            if (cargoAdder.getRole() == 3)
                return false;
        }
        cargo.setWarehouseId("1");
        // 插入货物信息到数据库
        int result = cargoDao.insert(cargo);
        if (result > 0) {
            // 发送货物信息到Kafka主题
            kafkaTemplate.send("cargo-info", gson.toJson(cargo.getCid()));
        }

        return result > 0;
    }

    /**
     * 提取货物
     * */
    @Override
    public Rest deleteCargo(String warehouse_id, String shelve_id, String row, String column) {
        QueryWrapper<ShelvesEntity> shelveswrapper = new QueryWrapper<>();
        shelveswrapper.eq("warehouse_id", warehouse_id)
                .eq("shelve_id", shelve_id)
                .eq("num_row", row)
                .eq("num_column", column);
        ShelvesEntity shelves = shelvesdao.selectOne(shelveswrapper);
        String cargo_id = shelves.getCargoId();
        Cargo cargo = cargoDao.selectById(cargo_id);

        cargo.setNum(cargo.getNum() - 1);
        if (cargo.getNum() == 0)
            cargoDao.deleteById(cargo_id);
        cargoDao.updateById(cargo);

        shelves.setCargoId("0");
        shelves.setStates("0");
        shelvesdao.updateById(shelves);

        return new Rest(Code.rc200.getCode(), "提取成功");
    }

    @Override
    public boolean updateCargo(Cargo cargo) {
        QueryWrapper<Cargo> cargowrapper = new QueryWrapper<>();
        cargowrapper.eq("cid",cargo.getCid());
        System.out.println(cargoDao.update(cargo,cargowrapper));
        return true;
    }

    @Override
    public Cargo getCargoById(String cid) {
        QueryWrapper<Cargo> cargowrapper = new QueryWrapper<>();
        cargowrapper.eq("cid",cid);

        return cargoDao.selectOne(cargowrapper);
    }

    @Override
    public Rest getCargoListByUserId(String userid) {
        QueryWrapper<Cargo> cargowrapper = new QueryWrapper<>();
        User user = userdao.selectById(userid);
        List<Cargo> cargolist = cargoDao.selectList(cargowrapper);

        if(user.getRole() == 3) {
            cargowrapper.eq("userid", userid);
            cargolist = cargoDao.selectList(cargowrapper);
        } else
            cargolist = cargoDao.selectList(cargowrapper);

        List<cargo_witelocation> locationlist = new ArrayList<>();
        for (Cargo c : cargolist){
            cargo_witelocation ca = new cargo_witelocation(c);
            ca.setShelve_location(getShelveIdOfCargo(String.valueOf(c.getCid())));
            locationlist.add(ca);
        }
        //如果用户是普通用户则只能获取自己的货物信息

        Map<Object, Object> ans = new HashMap<>();
        ans.put("CargoList", locationlist);
        return new Rest(Code.rc200.getCode(), ans,"货物信息列表");
    }


    @Override
    public String getShelveIdOfCargo(String cargoid) {
        QueryWrapper<ShelvesEntity> shelveswrapper = new QueryWrapper<>();
        shelveswrapper.eq("cargo_id", cargoid);
        List<ShelvesEntity> shelveslist = shelvesdao.selectList(shelveswrapper);
        String shelveid = "";
        for(ShelvesEntity se:shelveslist)
            shelveid += "S" + se.getShelveId() + "C" + String.valueOf((Integer.parseInt(se.getNumColumn()) - 1) * 10 + Integer.parseInt(se.getNumRow())) + ";";

        return shelveid;
    }

    @Override
    public Rest getLocationInfo(String[] locations) {

        return null;
    }

    @Override
    public Rest readyToRemove(String username, String cid, String num) {
        QueryWrapper<ShelvesEntity> shelveswrapper = new QueryWrapper<>();
        shelveswrapper.eq("cargo_id", cid);
        List<ShelvesEntity> shelves = shelvesdao.selectList(shelveswrapper);
        Collections.reverse(shelves);

        Cargo cargo = cargoDao.selectById(cid);
        cargo.setStatus(5);
        cargoDao.updateById(cargo);

        if(shelves.size() < Integer.parseInt(num))
            num = String.valueOf(shelves.size());


        for (int i = 0;i<Integer.parseInt(num);i++){
            ShelvesEntity se = shelves.get(i);
            se.setStates(username);
            shelvesdao.updateById(se);
        }

        return new Rest(Code.rc200.getCode(), "提取指令已发出");
    }
}
