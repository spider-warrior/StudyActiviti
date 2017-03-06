package com.jy.activiti.service.exception;

public enum ExceptionCode {

    /**
     * 404
     */
    SOURCE_NOT_FOUND_EXCEPTION("404"),

    /**
     * 400
     */
    PARAM_INVALID("400"),

    /**
     * 服务器内部异常
     */
    SERVER_INTERNAL_EXCEPTION("500");

    public static ExceptionCode getExceptionCode(String value) {
        for (ExceptionCode code : values()) {
            if (code.value.equals(value)) {
                return code;
            }
        }
        return null;
    }

    private String value;

    public String getValue() {
        return value;
    }

    ExceptionCode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExceptionCode{" +
                "value='" + value + '\'' +
                '}';
    }
}
