package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskWrapper extends BaseWrapper {

    /**
     * 任务名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * task definition key
     * */
    @JsonProperty("taskDefinitionKey")
    private String taskDefinitionKey;

    /**
     * 范畴
     */
    @JsonProperty("category")
    private String category;

    /**
     * 描述
     */
    @JsonProperty("description")
    private String description;

    /**
     * 任务持有人
     */
    @JsonProperty("owner")
    private UserWrapper owner;

    /**
     * 任务被指派人
     */
    @JsonProperty("assignee")
    private String assignee;

    /**
     * 力促哼实例ID
     */
    @JsonProperty("processInstancesId")
    private String processInstancesId;

    /**
     * 执行流ID
     */
    @JsonProperty("executionId")
    private String executionId;

    /**
     * 流程定义
     */
    @JsonProperty("processDefinition")
    private ProcessDefinitionWrapper processDefinitionWrapper;

    /**
     * 创建时间
     */
    @JsonProperty("createTime")
    private String createTime;

    /**
     * 变量
     * */
    @JsonProperty("variables")
    private Map<String, Object> variables;

    /**
     * 任务评论
     * */
    @JsonProperty("comments")
    private List<CommentWrapper> comments;

    public String getName() {
        return name;
    }

    public TaskWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public TaskWrapper setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
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

    public ProcessDefinitionWrapper getProcessDefinitionWrapper() {
        return processDefinitionWrapper;
    }

    public TaskWrapper setProcessDefinitionWrapper(ProcessDefinitionWrapper processDefinitionWrapper) {
        this.processDefinitionWrapper = processDefinitionWrapper;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public TaskWrapper setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public TaskWrapper setVariables(Map<String, Object> variables) {
        this.variables = variables;
        return this;
    }

    public List<CommentWrapper> getComments() {
        return comments;
    }

    public TaskWrapper setComments(List<CommentWrapper> comments) {
        this.comments = comments;
        return this;
    }
}
