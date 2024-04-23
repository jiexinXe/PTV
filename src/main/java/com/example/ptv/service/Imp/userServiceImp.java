package com.example.ptv.service.Imp;

import com.example.ptv.dao.UserDao;
import com.example.ptv.entity.User;
import com.example.ptv.service.userService;
import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class userServiceImp {
    @Autowired
    private UserDao userDao;

    /**
     * Param:userId:要查询信息的用户id
     * return:Rest类型对象，包含状态码，Data部分是User类型的对象
     * 用于查询某一特定用户的详细信息
     * */
    public Rest getUserinfo(Integer userId) {
        User user = userDao.selectById(userId);
        if (user == null) {
            return new Rest(Code.rc400.getCode(), "获取失败");
        } else {
            return new Rest(Code.rc200.getCode(), String.valueOf(user),"获取成功");
        }
    }
//    /**
// * Param:userId:用户对应的id
// * return:Rest类型对象，包含状态码和操作结果
// * 该功能用于删除数据库中的用户信息
// * */
//    public Rest deleteUser(String userId){
//    return new Rest(Code.rc200.getCode(), "删除成功");
//}
//    /**
//     * Param:userName:添加的用户的姓名;userRole:添加的用户的身份（个人，公司）;userInfo:添加的用户的其他信息（如果需要）
//     * return:Rest类型对象，包含状态码，Data部分为操作结果
//     * */
//    public Rest addUser(String userName, String userRole, String userInfo){
//        return new Rest(Code.rc200.getCode(), "操作结果");
//    }
    /**
     * Param:userId:所要修改信息的用户的id; changeItem:所要修改的属性; changeVariable:修改后的值
     * return:Rest类型变量,包含状态码，Data部分是修改结果
     * 用于管理员修改用户信息
     * */
    /**
     * Param:userId:所要修改信息的用户的id; changeItem:所要修改的属性; changeVariable:修改后的值
     * return:Rest类型变量,包含状态码，Data部分是修改结果
     * 用于管理员修改用户信息
     * */
    public Rest changeUserinfo(String userId, String changeItem, Object changeVariable){
        Integer id = Integer.parseInt(userId);
        User user = userDao.selectById(userId);
        if(user == null){
            return new Rest(Code.rc400.getCode(), "用户不存在");
        }
        try {
            switch (changeItem) {
                case "profile":
                    if(changeVariable instanceof Map){
                        Map<String, Object> profile = (Map<String, Object>) changeVariable;
                        if(profile.containsKey("name")){
                            user.setName((String) profile.get("name"));
                        }
                        if(profile.containsKey("username")){
                            user.setUsername((String) profile.get("username"));
                        }
                        if(profile.containsKey("gender")){
                            user.setGender((String) profile.get("gender"));
                        }
                        if(profile.containsKey("address")){
                            user.setAddress((String) profile.get("address"));
                        }
                        if(profile.containsKey("email")){
                            user.setEmail((String) profile.get("email"));
                        }
                        if(profile.containsKey("phone")){
                            user.setPhone((String) profile.get("phone"));
                        }
                    }
                    break;
                // 可以根据需要添加其他修改项的处理逻辑
                default:
                    return new Rest(Code.rc400.getCode(), "无效的修改项");
            }
            userDao.updateById(user);
            return new Rest(Code.rc200.getCode(), "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Rest(Code.rc400.getCode(), "修改失败");
        }
    }



//    /**
//     * Param
//     * return:一个包含所有user类型的对象的列表
//     * 功能是获取全体用户的简略信息
//     * */
//    public Rest getallUserinfo(){
//        return new Rest(Code.rc200.getCode(), "这里是用户信息的对象列表");
//    }
}
