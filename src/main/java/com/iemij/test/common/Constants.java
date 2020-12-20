package com.iemij.test.common;

public class Constants {
    //TODO:token有效期
    public static final long TOKEN_VALIDITY = 8 * 60 * 60 * 1000L;

    //TODO:admin token有效期
    public static final long ADMIN_TOKEN_VALIDITY = 10 * 60 * 1000L;

    //TODO:验证码有效期
    public static final long VERIFY_CODE_VALIDITY = 60 * 1000L;

    //TODO:验证码防刷存储数据有效期
    public static final long VERIFY_DATA_VALIDITY = 24;

    //TODO:X分钟内最多可发y要短信
    public static final long VERIFY_CODE_TIME_SCOPE = 10 * 60 * 1000L;

    //TODO:X分钟内最多可发y要短信
    public static final long VERIFY_CODE_NUM_SCOPE = 5;
}
