package com.pan.enums;

/**
 * 返回结果编码
 * @author tangpan
 */
public enum ResultEnum {

    SUCCESS(0, "成功"),
    EXCEPTION(404,"异常"),
    SAVESUCCESS(1, "保存成功"),
    SAVEERROR(2, "保存失败"),
    DELETESUCCESS(3, "删除成功"),
    DELETEERROR(4, "删除失败"),
    FAIL(10, "错误")
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
