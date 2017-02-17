package com.jy.activiti.common.enums;

/**
 * 资源类型
 * */
public enum ResourcesType {

    /**
     * 用户
     * */
    USER("user"),

    /**
     * 流程定义
     * */
    PROCESSDEFINITION("processdefinition");

    public static ResourcesType getResourcesType(String value) {
        for (ResourcesType type: values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public ResourcesType setValue(String value) {
        this.value = value;
        return this;
    }

    ResourcesType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ResourcesType{" +
                "value='" + value + '\'' +
                "} " + super.toString();
    }
}
