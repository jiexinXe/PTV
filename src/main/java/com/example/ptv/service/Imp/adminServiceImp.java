package com.example.ptv.service.Imp;

import com.example.ptv.service.adminService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.stereotype.Service;

@Service
public class adminServiceImp implements adminService {
    /**
     * Param
     * return:可用仓库的列表
     * 该功能用于返回可用仓库的列表
     * */
    public Rest fgetListofWH(){
        return new Rest(Code.rc200.getCode(), "这里是可用仓库的列表");
    }
    /**
     * Param:WHId:要查询的仓库的Id
     * return:该Id对应的仓库实体的对象
     * 用于查看单个仓库的详细信息
     * */
    public Rest fgetWHinfo(String WHId){
        return new Rest(Code.rc200.getCode(), "对应的WH类型的对象");
    }
    /**
     * Param:WHId:仓库的id; Item:要更改的属性; variable:修改后的值
     * return:操作结果
     * 该功能用于修改仓库信息
     * */
    public Rest fchangeWH(String WHId, String item, String variable){
        return new Rest(Code.rc200.getCode(), "操作结果");
    }
    /**
     * Param:WHId:仓库id
     * return:操作结果
     * 该功能用于管理员删除仓库
     * */
    public Rest fdeleteWH(String WHId){
        return new Rest(Code.rc200.getCode(), "操作结果");
    }
    /**
     *Param:WHtype:要添加的仓库的类型;maxContent:最大容纳量;nowContent:现已容纳量
     *return:操作结果
     * 该功能用于添加仓库
     * */
    public Rest faddWH(String type, String maxContext, String nowContext){
        return new Rest(Code.rc200.getCode(), "操作结果");
    }
}
