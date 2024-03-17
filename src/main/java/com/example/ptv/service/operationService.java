package com.example.ptv.service;

import com.example.ptv.utils.Code;
import com.example.ptv.utils.Rest;
import org.springframework.web.bind.annotation.RequestParam;

public interface operationService {
    public Rest fgetItemInfo(String id);
    public Rest fgetAllitemInfo(String id);

    public Rest fgetItem(String id);

    public Rest fdeposit(String name, String type,String info);
}
