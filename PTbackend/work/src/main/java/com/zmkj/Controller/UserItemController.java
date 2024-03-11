package com.zmkj.Controller;

import com.zmkj.entity.UserItemEntity;
import com.zmkj.service.UserItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userItem")
public class UserItemController {

    @Autowired
    private UserItemService userItemService; // 注入UserItemService的实例

    // 创建UserItem
    @PostMapping("/create")
    public UserItemEntity createUserItem(@RequestBody UserItemEntity userItemEntity) {
        return userItemService.createUserItem(userItemEntity);
    }

    // 获取所有UserItem
    @GetMapping("/getAll")
    public List<UserItemEntity> getAllUserItems() {
        return userItemService.getAllUserItems();
    }

    // 根据ID获取UserItem
    @GetMapping("/get/{id}")
    public UserItemEntity getUserItemById(@PathVariable Integer id) {
        return userItemService.getUserItemById(id);
    }

    // 更新UserItem
    @PutMapping("/update")
    public UserItemEntity updateUserItem(@RequestBody UserItemEntity userItemEntity) {
        return userItemService.updateUserItem(userItemEntity);
    }

    // 删除UserItem
    @DeleteMapping("/delete/{id}")
    public void deleteUserItem(@PathVariable Integer id) {
        userItemService.deleteUserItem(id);
    }
}