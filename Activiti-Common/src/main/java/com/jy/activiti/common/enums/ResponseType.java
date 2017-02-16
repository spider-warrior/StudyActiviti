package com.jy.activiti.common.enums;

public enum ResponseType {

    /**
     * Json
     * */
    Json("application/json"),

    /**
     * html
     * */
    Html("text/html");

    public ResponseType getResponseType(String type) {
        for (ResponseType t: values()) {
            if (t.equals(type)) {
                return t;
            }
        }
        return null;
    }

    /**
     * value
     * */
    private String value;

    public String getValue() {
        return value;
    }

    public ResponseType setValue(String value) {
        this.value = value;
        return this;
    }

    ResponseType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ResponseType{" +
                "value='" + value + '\'' +
                "} " + super.toString();
    }

}
