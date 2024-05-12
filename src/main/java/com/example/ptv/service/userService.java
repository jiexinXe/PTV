package com.example.ptv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ptv.dto.UserDTO;
import com.example.ptv.entity.User;
import com.example.ptv.utils.Rest;

import com.example.ptv.utils.Code;
import org.springframework.web.bind.annotation.RequestParam;

public interface userService extends IService<User> {






    Rest getUserinfo(Integer userId);

    Rest changeUserinfo(String userId, String changeItem, Object changeVariable);

    User getByName(String username);
}
