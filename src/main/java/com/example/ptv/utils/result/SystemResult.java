package com.example.ptv.utils.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemResult<T> implements Serializable {
    /**
     * 成功失败标识
     */
    private boolean flag;

    /**
     * 响应数据
     */
    private T data;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;

    public static Integer SUCCESS_200 = 200;
    public static Integer FAIL_500 = 500;

    public static <T> SystemResult<T> success() {
        return SystemResult.success(null);
    }

    public static <T> SystemResult<T> success(T result) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(true);
        systemResult.setData(result);
        systemResult.setMessage("成功");
        systemResult.setCode(SUCCESS_200);
        return systemResult;
    }

    public static <T> SystemResult<T> success(String msg) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(true);
        systemResult.setMessage(msg);
        return systemResult;
    }

    public static <T> SystemResult<T> fail(T result) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(false);
        systemResult.setCode(FAIL_500);
        systemResult.setData(result);
        return systemResult;
    }

    public static <T> SystemResult<T> fail(String msg) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(false);
        systemResult.setCode(FAIL_500);
        systemResult.setMessage(msg);
        return systemResult;
    }

    public static <T> SystemResult<T> fail(T result, String msg) {
        SystemResult<T> systemResult = new SystemResult<>();
        systemResult.setFlag(false);
        systemResult.setCode(FAIL_500);
        systemResult.setMessage(msg);
        systemResult.setData(result);
        return systemResult;
    }
}

