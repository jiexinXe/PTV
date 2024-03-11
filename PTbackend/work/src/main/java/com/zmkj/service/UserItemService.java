package com.zmkj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zmkj.entity.HistoryEntity;
import com.zmkj.entity.UserItemEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserItemService extends IService<UserItemEntity> {

    public default UserItemEntity createUserItem(@RequestBody UserItemEntity userItemEntity){
        return null;
    }

    public default List<UserItemEntity> getAllUserItems(){
        return null;
    }

    public default UserItemEntity getUserItemById(Integer id){
        return null;
    }

    public default UserItemEntity updateUserItem(UserItemEntity userItemEntity){
        return null;
    }

    public default void deleteUserItem( Integer id){

    }
}
