package com.iemij.test.common;

public class Response<T> {

    private int errcode;
    private String errmsg;
    private T data;

    public Response(int code, String msg, T data) {
        this.errcode = code;
        this.errmsg = msg;
        this.data = data;
    }

    public static <T> Response<T> succInstance() {
        return instance(Code.SUCCESS);
    }

    public static <T> Response<T> succInstance(T data) {
        return instance(Code.SUCCESS, data);
    }

    public static <T> Response<T> errInstance() {
        return instance(Code.SYSTEM_ERROR);
    }

    public static <T> Response<T> errInstance(String msg) {
        return instance(Code.SYSTEM_ERROR,msg);
    }

    public static <T> Response<T> instance(Code code) {
        return new Response<T>(code.code, code.msg, null);
    }

    public static <T> Response<T> instance(Code code, String msg) {
        return new Response<T>(code.code, msg, null);
    }

    public static <T> Response<T> instance(Code code, T data) {
        return new Response<T>(code.code, code.msg, data);
    }

    public static <T> Response<T> instance(Code code, String msg, T data) {
        return new Response<T>(code.code, msg, data);
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
