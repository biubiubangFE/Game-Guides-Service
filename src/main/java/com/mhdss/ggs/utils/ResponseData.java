package com.mhdss.ggs.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

public class ResponseData<T> {

    private int errcode;
    private String errmsg;
    private T data;

    public ResponseData() {
        super();
    }

    private ResponseData(int errcode, String errmsg, T data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

    private ResponseData(ErrorCode errorCode, T data) {
        this(errorCode.getCode(), errorCode.getMsg(), data);
    }

    private ResponseData(ErrorCode errcode) {
        this(errcode, null);
    }

    public static ResponseData success(Collection<?> data) {
        if (data == null) {
            return emptyCollection();
        }
        return new ResponseData<>(ErrorCode.SUCCESS, data);
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<T>(ErrorCode.SUCCESS, data);
    }

    public static ResponseData success() {
        return new ResponseData(ErrorCode.SUCCESS);
    }

    public static <T> ResponseData<T> error(ErrorCode code) {
        return new ResponseData<>(code, null);
    }

    public static <T> ResponseData<T> error(ErrorCode code, T data) {
        return new ResponseData<T>(code, data) {
        };
    }

    public static ResponseData<?> empty() {
        return new ResponseData(ErrorCode.CONTENT_EMPTY);
    }

    public static ResponseData<?> emptyCollection() {
        return new ResponseData<>(ErrorCode.SUCCESS, CollectionUtils.EMPTY_COLLECTION);
    }


    public static ResponseData<?> notice(String message) {
        ResponseData<?> data = new ResponseData<>();
        data.setErrcode(5);
        data.setErrmsg(message);
        return data;
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
