package com.chan.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * server返回给client的结果类
 */
@Data
public class Result implements Serializable {

    private Integer code; // 200: 正常 非200:异常
    private String msg;
    private Object data;

    private static Result instance = new Result();

    private Result() {
    }

    public static Result success(Integer code, String msg, Object data) {
        instance.setCode(code);
        instance.setMsg(msg);
        instance.setData(data);
        return instance;
    }


    public static Result success(Object data) {
        return success(200, "操作成功", data);
    }

    public static Result success() {
        return success(200, "操作成功", null);
    }

    public static Result fail(Integer code, String msg, Object data) {
        instance.setCode(code);
        instance.setMsg(msg);
        instance.setData(data);
        return instance;
    }

    public static Result fail(Integer code, String msg) {
        return fail(code, msg, null);
    }

    public static Result fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

}
