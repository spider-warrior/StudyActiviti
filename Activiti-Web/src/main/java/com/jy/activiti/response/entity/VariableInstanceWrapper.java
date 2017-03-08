package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariableInstanceWrapper extends BaseWrapper{

    @JsonProperty("name")
    private String name;

    @JsonProperty("processInstanceId")
    private String processInstanceId;

    @JsonProperty("executionId")
    private String executionId;

    @JsonProperty("taskId")
    private String taskId;

    @JsonProperty("value")
    private Object value;

    @JsonProperty("typeName")
    private String typeName;

    public String getName() {
        return name;
    }

    public VariableInstanceWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public VariableInstanceWrapper setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getExecutionId() {
        return executionId;
    }

    public VariableInstanceWrapper setExecutionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public VariableInstanceWrapper setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public VariableInstanceWrapper setValue(Object value) {
        this.value = value;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public VariableInstanceWrapper setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }
}
