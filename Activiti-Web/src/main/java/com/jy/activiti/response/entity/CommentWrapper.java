package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentWrapper extends BaseWrapper {

    @JsonProperty("commentUser")
    private UserWrapper commentUser;

    @JsonProperty("createTime")
    private String createTime;

    @JsonProperty("taskId")
    private String taskId;

    @JsonProperty("processInstanceId")
    private String processInstanceId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("content")
    private String content;

    public UserWrapper getCommentUser() {
        return commentUser;
    }

    public CommentWrapper setCommentUser(UserWrapper commentUser) {
        this.commentUser = commentUser;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public CommentWrapper setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public CommentWrapper setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public CommentWrapper setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public String getType() {
        return type;
    }

    public CommentWrapper setType(String type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentWrapper setContent(String content) {
        this.content = content;
        return this;
    }
    public static class CommentWrapperConfig extends BaseWrapper.Config{

        private boolean needCommentUser = true;
        private boolean needCreateTime = true;
        private boolean needTaskId = true;
        private boolean needProcessInstanceId = true;
        private boolean needType = true;
        private boolean needContent = true;

        public boolean isNeedCommentUser() {
            return needCommentUser;
        }

        public CommentWrapperConfig setNeedCommentUser(boolean needCommentUser) {
            this.needCommentUser = needCommentUser;
            return this;
        }

        public boolean isNeedCreateTime() {
            return needCreateTime;
        }

        public CommentWrapperConfig setNeedCreateTime(boolean needCreateTime) {
            this.needCreateTime = needCreateTime;
            return this;
        }

        public boolean isNeedTaskId() {
            return needTaskId;
        }

        public CommentWrapperConfig setNeedTaskId(boolean needTaskId) {
            this.needTaskId = needTaskId;
            return this;
        }

        public boolean isNeedProcessInstanceId() {
            return needProcessInstanceId;
        }

        public CommentWrapperConfig setNeedProcessInstanceId(boolean needProcessInstanceId) {
            this.needProcessInstanceId = needProcessInstanceId;
            return this;
        }

        public boolean isNeedType() {
            return needType;
        }

        public CommentWrapperConfig setNeedType(boolean needType) {
            this.needType = needType;
            return this;
        }

        public boolean isNeedContent() {
            return needContent;
        }

        public CommentWrapperConfig setNeedContent(boolean needContent) {
            this.needContent = needContent;
            return this;
        }
    }
}
