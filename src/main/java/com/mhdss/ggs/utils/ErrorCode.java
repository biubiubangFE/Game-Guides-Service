package com.mhdss.ggs.utils;

public enum ErrorCode {

    SUCCESS(0, "success."),
    UNKNOWN_ERROR(1, "have a system error."),
    NO_LOGIN(3, "you must login first."),
    CONTENT_EMPTY(0, "gets the content is empty."),
    HTTP_MESSAGE_READ_ERROR(2, "request message read error."),
    PARAMETER_ERROR(4, "Parameter error"),
    NO_HANDLER_FOUND(2, "no handle found error."),

    NO_PAY(1000, "this resource no pay"),
    RESOURCE_NO_EXIST(1001, "this resource not exist"),

    //imgSecCheck
    IMG_SEC_CHECK_ERROR(1, "上传失败，图片含有违法违规内容"),
    ERROR_PERMISSION_DENIED(1, "权限不足");

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
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
