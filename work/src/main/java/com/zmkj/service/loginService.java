package com.example.ptv.service;

import com.example.ptv.utils.Rest;

/**
 * 该service对应logincontroller
 * */
public interface loginService {
    public Rest fchecklogin(String userId, String passWord);

    public Rest fregister(String userName, String passWord, String userRole);
}
