package com.iemij.test.common;

import java.util.HashMap;
import java.util.Map;

public enum Code {

    SYSTEM_ERROR(-1, "系统繁忙"),
    SUCCESS(0, "成功"),
    NEED_LOGIN(10000, "请先登录"),
    LOGIN_EXPIRE(10001, "登录超时"),
    MATCH_VERIFY_ERR(10002, "验证码错误"),
    EXPIRE_VERIFY_ERR(10003, "验证码超时"),
    SEND_VERIFY_TOO_OFEN_ERR(10003, "发送验证码过于频繁")
    ;

    public Integer code;
    public String msg;

    Code(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static Map<Integer, Code> codeMap = new HashMap<Integer, Code>();

    static {
        for (Code code : Code.values()) {
            codeMap.put(code.code, code);
        }
    }

    public static Code getCode(Integer code) {
        return codeMap.get(code);
    }

}
