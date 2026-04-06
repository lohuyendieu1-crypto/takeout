package com.sky.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 後端返回統一結果
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; // 編碼：1成功，0和其它數字為失敗
    private String msg; // 錯誤信息
    private T data; // 數據

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
