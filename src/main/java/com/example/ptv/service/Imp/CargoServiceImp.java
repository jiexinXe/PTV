package com.example.ptv.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ptv.dao.CargoDao;
import com.example.ptv.dao.UserDao;
import com.example.ptv.entity.Cargo;
import com.example.ptv.entity.User;
import com.example.ptv.service.CargoService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        private KafkaTemplate<String ,Object> kafkaTemplate;
        @Autowired
        private Gson gson = new GsonBuilder().create();
        @Autowired
        private UserDao userdao;

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
    public Rest deleteCargo(String cid, String num) {
        QueryWrapper<Cargo> cargowrapper = new QueryWrapper<>();
        cargowrapper.eq("cid", cid);

        Cargo cargo = cargoDao.selectOne(cargowrapper);

        if(cargo.getNum() < Integer.valueOf(num))
            return new Rest(Code.rc400.getCode(),"余量不足");

        System.out.println(cargo);


        Integer num_new = cargo.getNum()-Integer.parseInt(num);

//        Integer num_new = cargo.getNum()-Integer.valueOf(num);
        if(num_new == 0){
            cargoDao.delete(cargowrapper);
            return new Rest(Code.rc200.getCode(), "成功提取，余量已用完");
        }

        cargo.setNum(num_new);
        if(num_new == 0){
            cargoDao.delete(cargowrapper);
            return new Rest(Code.rc200.getCode(), "成功提取，余量已用完");
        }
        cargoDao.update(cargo,cargowrapper);
        return new Rest(Code.rc200.getCode(),"成功提取");
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
        List<Cargo> cargolist;
        //如果用户是普通用户则只能获取自己的货物信息
        if(user.getRole() == 3) {
            cargowrapper.eq("userid", userid);
            cargolist = cargoDao.selectList(cargowrapper);
            Map<Object, Object> ans = new HashMap<>();
            ans.put("CargoList", cargolist);
            return new Rest(Code.rc200.getCode(), ans,"普通用户货物列表");
        }
        cargolist = cargoDao.selectList(cargowrapper);
        Map<Object, Object> ans = new HashMap<>();
        ans.put("CargoList", cargolist);
        return new Rest(Code.rc200.getCode(), ans,"管理员货物列表");

    }
}
