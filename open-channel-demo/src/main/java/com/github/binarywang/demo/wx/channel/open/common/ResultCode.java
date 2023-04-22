package com.github.binarywang.demo.wx.channel.open.common;

/**
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
public class ResultCode {

    private final int code;
    private final String msg;

    public static ResultCode SUCCESS = new ResultCode(0, "ok");
    public static ResultCode ERROR = new ResultCode(-1, "error");

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
