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
    @JsonProperty("taskName")
    private String taskName;

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

    public String getTaskName() {
        return taskName;
    }

    public TaskWrapper setTaskName(String taskName) {
        this.taskName = taskName;
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

    public static class TaskWrapperConfig extends BaseWrapper.Config {

        private boolean needTaskName = true;
        private boolean needTaskDefinitionKey = true;
        private boolean needCategory = true;
        private boolean needDescription = true;
        private boolean needOwner = true;
        private boolean needAssignee = true;
        private boolean needProcessInstancesId = true;
        private boolean needExecutionId = true;
        private boolean needProcessDefinitionWrapper = true;
        private boolean needCreateTime = true;
        private boolean needVariables = true;
        private boolean needComments = true;

        public boolean isNeedTaskName() {
            return needTaskName;
        }

        public TaskWrapperConfig setNeedTaskName(boolean needTaskName) {
            this.needTaskName = needTaskName;
            return this;
        }

        public boolean isNeedTaskDefinitionKey() {
            return needTaskDefinitionKey;
        }

        public TaskWrapperConfig setNeedTaskDefinitionKey(boolean needTaskDefinitionKey) {
            this.needTaskDefinitionKey = needTaskDefinitionKey;
            return this;
        }

        public boolean isNeedCategory() {
            return needCategory;
        }

        public TaskWrapperConfig setNeedCategory(boolean needCategory) {
            this.needCategory = needCategory;
            return this;
        }

        public boolean isNeedDescription() {
            return needDescription;
        }

        public TaskWrapperConfig setNeedDescription(boolean needDescription) {
            this.needDescription = needDescription;
            return this;
        }

        public boolean isNeedOwner() {
            return needOwner;
        }

        public TaskWrapperConfig setNeedOwner(boolean needOwner) {
            this.needOwner = needOwner;
            return this;
        }

        public boolean isNeedAssignee() {
            return needAssignee;
        }

        public TaskWrapperConfig setNeedAssignee(boolean needAssignee) {
            this.needAssignee = needAssignee;
            return this;
        }

        public boolean isNeedProcessInstancesId() {
            return needProcessInstancesId;
        }

        public TaskWrapperConfig setNeedProcessInstancesId(boolean needProcessInstancesId) {
            this.needProcessInstancesId = needProcessInstancesId;
            return this;
        }

        public boolean isNeedExecutionId() {
            return needExecutionId;
        }

        public TaskWrapperConfig setNeedExecutionId(boolean needExecutionId) {
            this.needExecutionId = needExecutionId;
            return this;
        }

        public boolean isNeedProcessDefinitionWrapper() {
            return needProcessDefinitionWrapper;
        }

        public TaskWrapperConfig setNeedProcessDefinitionWrapper(boolean needProcessDefinitionWrapper) {
            this.needProcessDefinitionWrapper = needProcessDefinitionWrapper;
            return this;
        }

        public boolean isNeedCreateTime() {
            return needCreateTime;
        }

        public TaskWrapperConfig setNeedCreateTime(boolean needCreateTime) {
            this.needCreateTime = needCreateTime;
            return this;
        }

        public boolean isNeedVariables() {
            return needVariables;
        }

        public TaskWrapperConfig setNeedVariables(boolean needVariables) {
            this.needVariables = needVariables;
            return this;
        }

        public boolean isNeedComments() {
            return needComments;
        }

        public TaskWrapperConfig setNeedComments(boolean needComments) {
            this.needComments = needComments;
            return this;
        }
    }
}
