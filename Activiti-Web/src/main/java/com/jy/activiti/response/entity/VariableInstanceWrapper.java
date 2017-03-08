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
    
    public static class VariableInstanceWrapperConfig extends BaseWrapper.Config {
        
        private boolean needName;
        private boolean needProcessInstanceId;
        private boolean needExecutionId;
        private boolean needTaskId;
        private boolean needValue;
        private boolean needTypeName;

        public boolean isNeedName() {
            return needName;
        }

        public VariableInstanceWrapperConfig setNeedName(boolean needName) {
            this.needName = needName;
            return this;
        }

        public boolean isNeedProcessInstanceId() {
            return needProcessInstanceId;
        }

        public VariableInstanceWrapperConfig setNeedProcessInstanceId(boolean needProcessInstanceId) {
            this.needProcessInstanceId = needProcessInstanceId;
            return this;
        }

        public boolean isNeedExecutionId() {
            return needExecutionId;
        }

        public VariableInstanceWrapperConfig setNeedExecutionId(boolean needExecutionId) {
            this.needExecutionId = needExecutionId;
            return this;
        }

        public boolean isNeedTaskId() {
            return needTaskId;
        }

        public VariableInstanceWrapperConfig setNeedTaskId(boolean needTaskId) {
            this.needTaskId = needTaskId;
            return this;
        }

        public boolean isNeedValue() {
            return needValue;
        }

        public VariableInstanceWrapperConfig setNeedValue(boolean needValue) {
            this.needValue = needValue;
            return this;
        }

        public boolean isNeedTypeName() {
            return needTypeName;
        }

        public VariableInstanceWrapperConfig setNeedTypeName(boolean needTypeName) {
            this.needTypeName = needTypeName;
            return this;
        }
    }
}
