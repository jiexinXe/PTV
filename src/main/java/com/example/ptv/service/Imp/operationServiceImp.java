package com.example.ptv.service.Imp;

import com.example.ptv.service.operationService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.stereotype.Service;

@Service
public class operationServiceImp implements operationService {
    /**
     * Param:id:货物的id
     * return:id对应的货物的实体对象
     * 该功能用于货物货物的详细信息
     * */
    public Rest fgetItemInfo(String id){
        return new Rest(Code.rc200.getCode(), "该货物的实体对象");
    }
    /**
     * Param:id:用户的id
     * return:该用户所有货物的信息
     * 该功能用于用户查询自己存放的所有货物的列表
     * */
    public Rest fgetAllitemInfo(String id){
        return new Rest(Code.rc200.getCode(), "该用户的所有货物的简要信息");

    }
    /**
     * Param:id:货物对应的id
     * return:对应货物的实体对象，以及操作结果
     * 该功能用于客户取出单个货物
     * 在返回信息的同时，会执行货物的删除操作
     * */
    public Rest fgetItem(String id){
        return new Rest(Code.rc200.getCode(), "item类型的对象，操作结果");
    }
    /**
     * Param:name:货物名称; type:货物类型; info:货物其他信息
     * return: 操作结果
     * 该功能用于用户存放货物
     * */
    public Rest fdeposit(String name, String type,String info){
        return new Rest(Code.rc200.getCode(), "操作结果");
    }
}
