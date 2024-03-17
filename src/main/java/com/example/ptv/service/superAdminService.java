package com.example.ptv.service;

import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.web.bind.annotation.RequestParam;

public interface superAdminService {
    public Rest fdeleteUser(String userId);

    public Rest faddUser(String userName, String userRole, String userInfo);

    public Rest fchangeUserinfo(String userId, String changeItem, String changeVatiable);

    public Rest fgetUserinfo(String userId);

    public Rest fgetallUserinfo();
}
