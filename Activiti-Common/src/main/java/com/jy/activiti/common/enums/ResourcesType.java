package com.jy.activiti.common.enums;

/**
 * 资源类型
 */
public enum ResourcesType {

    /**
     * 用户
     */
    USER("user"),

    /**
     * 用户组
     */
    GROUP("group"),

    /**
     * 流程定义
     */
    PROCESSDEFINITION("processdefinition"),

    /**
     * 流程模型
     */
    PROCESSDEFINITION_MODEL("processdefinition_model"),

    /**
     * 流程文件
     */
    PROCESSDEFINITION_DOC("processdefinition_doc"),

    /**
     * 流程图
     */
    PROCESSDEFINITION_IMG("processdefinition_img"),

    /**
     * 流程实例
     * */
    PROCESSINSTANCE("processinstance"),

    /**
     * 历史流程实例
     * */
    HISTORICPROCESSINSTANCE("historic_process_instance");

    public static ResourcesType getResourcesType(String value) {
        for (ResourcesType type : values()) {
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
