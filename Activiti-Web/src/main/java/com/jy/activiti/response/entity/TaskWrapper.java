package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskWrapper extends BaseWrapper{

    /**
     * 任务名称
     * */
    @JsonProperty("name")
    private String name;

    /**
     * 范畴
     * */
    @JsonProperty("category")
    private String category;

    /**
     * 描述
     * */
    @JsonProperty("description")
    private String description;

    /**
     * 任务持有人
     * */
    @JsonProperty("owner")
    private UserWrapper owner;

    /**
     * 任务被指派人
     * */
    @JsonProperty("assignee")
    private String assignee;

    /**
     * 力促哼实例ID
     * */
    @JsonProperty("processInstancesId")
    private String processInstancesId;

    /**
     * 执行流ID
     * */
    @JsonProperty("executionId")
    private String executionId;

    /**
     * 流程定义ID
     * */
    @JsonProperty("processDefinitionId")
    private String processDefinitionId;

    /**
     * 创建时间
     * */
    @JsonProperty("createTime")
    private String createTime;

    public String getName() {
        return name;
    }

    public TaskWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public TaskWrapper setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskWrapper setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserWrapper getOwner() {
        return owner;
    }

    public TaskWrapper setOwner(UserWrapper owner) {
        this.owner = owner;
        return this;
    }

    public String getAssignee() {
        return assignee;
    }

    public TaskWrapper setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getProcessInstancesId() {
        return processInstancesId;
    }

    public TaskWrapper setProcessInstancesId(String processInstancesId) {
        this.processInstancesId = processInstancesId;
        return this;
    }

    public String getExecutionId() {
        return executionId;
    }

    public TaskWrapper setExecutionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public TaskWrapper setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public TaskWrapper setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "TaskWrapper{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", assignee='" + assignee + '\'' +
                ", processInstancesId='" + processInstancesId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
