package com.example.ptv.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rest {
    @JsonProperty("Code")
    private Integer Code;
    private Object data;
    @JsonProperty("msg")
    private String msg;

    public Rest(Integer code,String msg){
        this.Code = code;
        this.msg = msg;
    }

}
