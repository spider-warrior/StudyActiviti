package com.jy.activiti.common.exception;

import com.jy.activiti.common.enums.ResponseType;

/**
 * 用户未登录异常
 * */
public class NonUserLoginException extends RuntimeException{

    private ResponseType responseType;

    public ResponseType getResponseType() {
        return responseType;
    }

    public NonUserLoginException setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public NonUserLoginException(ResponseType responseType) {
        this.responseType = responseType;
    }

    @Override
    public String toString() {
        return "NonUserLoginException{" +
                "responseType=" + responseType +
                "} " + super.toString();
    }
}
