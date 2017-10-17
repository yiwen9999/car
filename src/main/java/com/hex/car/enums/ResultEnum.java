package com.hex.car.enums;

public enum ResultEnum {
    UN_KNOW_ERRO(-1,"未知错误"),
    SUCCESS(0,"成功"),
    UN_LOGIN(101,"未登陆"),
    UPLOAD_FAIL(102,"上传失败"),
    ERROR_PARAM(103,"传递的参数错误"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
