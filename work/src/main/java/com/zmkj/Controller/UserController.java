package com.zmkj.Controller;

import com.zmkj.entity.UserEntity;
import com.zmkj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    // 这里使用@Autowired注解来自动装配UserService的bean
    @Autowired
    private UserService userService;

    // 创建用户
    @PostMapping("/create")
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return userService.createUser(userEntity);
    }

    // 获取所有用户
    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    // 根据用户ID获取用户
    @GetMapping("/{userId}")
    public UserEntity getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    // 更新用户信息
    @PutMapping("/update")
    public UserEntity updateUser(@RequestBody UserEntity userEntity) {
        return userService.updateUser(userEntity);
    }

    // 删除用户
    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}