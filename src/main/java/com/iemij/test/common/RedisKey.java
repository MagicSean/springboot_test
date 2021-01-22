package com.iemij.test.common;

public class RedisKey {

    public static final String prefix = "test:";

    public static String verifyCode(String phone) {
        return prefix + "verify:" + phone;
    }

    public static String uid(String uid) {
        return prefix + "uid:" + uid;
    }

}
