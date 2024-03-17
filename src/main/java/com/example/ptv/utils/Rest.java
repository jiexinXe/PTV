package com.example.ptv.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rest {
    private Integer Code;
    private Object data;
    private String msg;

    public Rest(Integer code,String msg){
        this.Code = code;
        this.msg = msg;
    }

}
