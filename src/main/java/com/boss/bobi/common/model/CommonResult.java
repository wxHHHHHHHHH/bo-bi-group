package com.boss.bobi.common.model;


import com.boss.bobi.common.enums.ResultCode;
import lombok.NoArgsConstructor;

import java.util.Collections;

/**
 * 通用返回结果
 * @author gyq
 * @param <T>
 */
@NoArgsConstructor
public class CommonResult <T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> CommonResult<T> build(ResultCode resultCode){
        return new CommonResult(resultCode.result, resultCode.msg, Collections.emptyMap());
    }

    public static <T> CommonResult<T> build(ResultCode resultCode, T data){
        return new CommonResult(resultCode.result, resultCode.msg, data);
    }

    public CommonResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
