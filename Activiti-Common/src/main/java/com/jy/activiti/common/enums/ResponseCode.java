package com.jy.activiti.common.enums;

/**
 * 响应吗
 * */
public enum ResponseCode {

    /**
     * 成功
     * */
    SUCCESS("200"),

    /**
     * 请求参数异常
     * */
    REQUEST_PARAM_ERROR("400"),

    /**
     *
     * 请求资源未找到*/
    REQUEST_SOURCE_NOT_FOUND("404"),

    /**
     * 请求不被允许
     * */
    REQUEST_NOT_ALLOWED("405"),

    /**
     * 服务器内部异常
     * */
    SERVER_INTERNAL_EXCEPTION("500");

    private String value;

    public String getValue() {
        return value;
    }

    public ResponseCode setValue(String value) {
        this.value = value;
        return this;
    }

    ResponseCode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "value='" + value + '\'' +
                "} " + super.toString();
    }
}
