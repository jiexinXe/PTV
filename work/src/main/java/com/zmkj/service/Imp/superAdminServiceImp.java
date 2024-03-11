package com.example.ptv.service.Imp;

import com.example.ptv.service.superAdminService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.stereotype.Service;

@Service
public class superAdminServiceImp implements superAdminService {
    /**
     * Param:userId:用户对应的id
     * return:Rest类型对象，包含状态码和操作结果
     * 该功能用于删除数据库中的用户信息
     * */
    public Rest fdeleteUser(String userId){
        return new Rest(Code.rc200.getCode(), "删除成功");
    }
    /**
     * Param:userName:添加的用户的姓名;userRole:添加的用户的身份（个人，公司）;userInfo:添加的用户的其他信息（如果需要）
     * return:Rest类型对象，包含状态码，Data部分为操作结果
     * */
    public Rest faddUser(String userName, String userRole, String userInfo){
        return new Rest(Code.rc200.getCode(), "操作结果");
    }
    /**
     * Param:userId:所要修改信息的用户的id; changeItem:所要修改的属性; changeVariable:修改后的值
     * return:Rest类型变量,包含状态码，Data部分是修改结果
     * 用于管理员修改用户信息
     * */
    public Rest fchangeUserinfo(String userId, String changeItem, String changeVatiable){
        return new Rest(Code.rc200.getCode(), "修改结果");
    }
    /**
     * Param:userId:要查询信息的用户id
     * return:Rest类型对象，包含状态码，Data部分是User类型的对象
     * 用于查询某一特定用户的详细信息
     * */
    public Rest fgetUserinfo(String userId){
        return new Rest(Code.rc200.getCode(), "这里是user类型对象");
    }
    /**
     * Param
     * return:一个包含所有user类型的对象的列表
     * 功能是获取全体用户的简略信息
     * */
    public Rest fgetallUserinfo(){
        return new Rest(Code.rc200.getCode(), "这里是用户信息的对象列表");
    }
}
