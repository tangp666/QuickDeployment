package com.pan.enums;

/**
 * 返回结果编码
 * @author tangpan
 */
public enum ResultEnum {

    SUCCESS(0, "成功"),
    EXCEPTION(404,"异常")
    ;

    private int code;

    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
