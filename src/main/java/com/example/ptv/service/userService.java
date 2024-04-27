package com.example.ptv.service;

import com.example.ptv.utils.Rest;

import com.example.ptv.utils.Code;
import org.springframework.web.bind.annotation.RequestParam;

public interface userService {
    public Rest deleteUser(String userId);

    public Rest addUser(String userName, String userRole, String userInfo);

    public Rest changeUserinfo(String userId, String changeItem, String changeVatiable);

    public Rest getUserinfo(String userId);

    public Rest getallUserinfo();
}
