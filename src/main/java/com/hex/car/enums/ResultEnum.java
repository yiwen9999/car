package com.hex.car.enums;

public enum ResultEnum {
    UN_KNOW_ERRO(-1, "未知错误"),
    SUCCESS(0, "成功"),
    UN_LOGIN(101, "未登录"),
    UPLOAD_FAIL(102, "上传失败"),
    ERROR_PARAM(103, "传递的参数错误"),
    ERROR_DELETE(104, "未能删除"),
    ERROR_LOGIN(105, "登录失败"),
    ERROR_PASSWORD(106, "密码错误"),
    ERROR_IDENTITY(107, "身份错误"),
    ERROR_MOBILE(108, "该手机号已被使用"),
    ERROR_USERNAME(109, "该用户名已被使用"),
    ERROR_SMSCODE(110, "手机号或验证码错误"),
    ERROR_NULLPARAM(111, "不能为空"),;

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
