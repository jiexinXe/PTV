package com.zmkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zmkj.entity.UserEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService extends IService<UserEntity> {

    public  default  UserEntity createUser( UserEntity userEntity){
        return null;
    }

    public  default List<UserEntity> getAllUsers(){
        return null;
    }

    public default  UserEntity getUserById( Integer userId){
        return null;
    }

    public default UserEntity updateUser(UserEntity userEntity){
        return null;
    }

    public default void deleteUser(@PathVariable Integer userId){

    }
}
