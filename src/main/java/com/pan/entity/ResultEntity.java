package com.pan.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 返回对象
 * @author tangpan
 */
public class ResultEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 编码 */
    private int code;
    /* 消息 */
    private String message;
    /* 返回数据  data {total : 0, rows: 列表集合} 或 {data:数据} */
    private JSONObject data;

    public ResultEntity() {
    }

    /**
     * 没有参数返回对象
     * @param code 编码
     * @param message 返回信息
     */
    public ResultEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 带有参数的返回对象
     * @param code 编码
     * @param message 返回信息
     * @param data 返回数据  data {total : 0, rows: 列表集合} 或 {data:数据}
     */
    public ResultEntity(int code, String message, JSONObject data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
