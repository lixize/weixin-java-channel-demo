package com.github.binarywang.demo.wx.channel.normal.common;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class Result<T> implements Serializable {
    /** 返回码 */
    private int code;
    /** 返回信息 */
    private String msg;
    /** 返回数据 */
    private T data;


    public static <T> Result<T> success(T data){
        return new  Result<T>(data);
    }

    public static <T> Result<T> error(ResultCode rc){
        return new  Result<T>(rc);
    }

    public static <T> Result<T> error(int code, String msg){
        return new  Result<T>(code, msg);
    }

    private Result(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    private Result(ResultCode rc) {
        if(rc == null) {
            return;
        }
        this.code = rc.getCode();
        this.msg = rc.getMsg();
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public T getData() {
        return data;
    }
}
