package com.example.ptv.controller;

import com.example.ptv.service.Imp.adminServiceImp;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.apache.ibatis.javassist.compiler.ast.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.ptv.service.adminService;
/**
 * 本Controller类为仓库管理员请求处理，用于管理与仓库管理相关的请求，与superadmin不同
 * 方法中的WH均为Warehouse的缩写
 * */

@Controller
@RequestMapping("/admin")
public class adminController {


    @Autowired
    private adminServiceImp adminserviceimp;

    /**
     * Param
     * return:可用仓库的列表
     * 该功能用于返回可用仓库的列表
     * */
    @GetMapping("/listOfWH")
    public Rest getListofWH(){
        return adminserviceimp.fgetListofWH();
    }


    /**
     * Param:WHId:要查询的仓库的Id
     * return:该Id对应的仓库实体的对象
     * 用于查看单个仓库的详细信息
     * */
    @GetMapping("/getWHinfo/{WHId}")
    public Rest getWHinfo(@PathVariable("WHId") String WHId){
        return adminserviceimp.fgetWHinfo(WHId);
    }

    /**
     * Param:WHId:仓库的id; Item:要更改的属性; variable:修改后的值
     * return:操作结果
     * 该功能用于修改仓库信息
     * */
    @PostMapping("/changeWH")
    public Rest changeWH(@RequestParam("WHId") String WHId, @RequestParam("changeItem") String Item, @RequestParam("changeVariable") String variable){
        return adminserviceimp.fchangeWH(WHId, Item, variable);
    }


    /**
     * Param:WHId:仓库id
     * return:操作结果
     * 该功能用于管理员删除仓库
     * */
    @PostMapping("/deleteWH")
    public Rest deleteWH(@RequestParam("WHId") String WHId){
        return adminserviceimp.fdeleteWH(WHId);
    }

    /**
     *Param:WHtype:要添加的仓库的类型;maxContent:最大容纳量;nowContent:现已容纳量
     *return:操作结果
     * 该功能用于添加仓库
     * */
    @PostMapping("/addWH")
    public Rest addWH(@RequestParam("WHtype") String type, @RequestParam("WHmaxcontent") String maxContent, @RequestParam("WHnowcontent") String nowContent){
        return adminserviceimp.faddWH(type,maxContent,nowContent);
    }
}
