package com.iemij.test.common;

import java.util.HashMap;
import java.util.Map;

public enum Code {

    SYSTEM_ERROR(-1, "系统繁忙"),
    SUCCESS(0, "成功")
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
