package com.jy.activiti.common.enums;

public enum Role {

    /**
     * 职员
     * */
    WORKER("worker"),

    /**
     * 主管
     * */
    LEADER("leader"),

    /**
     * 经理
     * */
    MANAGER("manager"),

    /**
     * 老板
     * */
    BOSS("boss");


    private String value;

    public String getValue() {
        return value;
    }

    public Role setValue(String value) {
        this.value = value;
        return this;
    }

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Role{" +
                "value='" + value + '\'' +
                '}';
    }
}
