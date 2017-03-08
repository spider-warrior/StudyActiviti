package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDefinitionWrapper extends BaseWrapper {

    @JsonProperty("processDefinitionName")
    private String processDefinitionName;

    @JsonProperty("businessKey")
    private String businessKey;

    @JsonProperty("startUsers")
    private List<UserWrapper> startUsers;


    @JsonProperty("startGroups")
    private List<GroupWrapper> startGroups;

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public ProcessDefinitionWrapper setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
        return this;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public ProcessDefinitionWrapper setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public List<UserWrapper> getStartUsers() {
        return startUsers;
    }

    public ProcessDefinitionWrapper setStartUsers(List<UserWrapper> startUsers) {
        this.startUsers = startUsers;
        return this;
    }

    public List<GroupWrapper> getStartGroups() {
        return startGroups;
    }

    public ProcessDefinitionWrapper setStartGroups(List<GroupWrapper> startGroups) {
        this.startGroups = startGroups;
        return this;
    }
    public static class ProcessDefinitionWrapperConfig extends BaseWrapper.Config {

        private boolean processDefinitionName = true;
        private boolean needBusinessKey = true;
        private boolean needStartUsers = true;
        private boolean needStartGroups = true;

        public boolean isProcessDefinitionName() {
            return processDefinitionName;
        }

        public ProcessDefinitionWrapperConfig setProcessDefinitionName(boolean processDefinitionName) {
            this.processDefinitionName = processDefinitionName;
            return this;
        }

        public boolean isNeedBusinessKey() {
            return needBusinessKey;
        }

        public ProcessDefinitionWrapperConfig setNeedBusinessKey(boolean needBusinessKey) {
            this.needBusinessKey = needBusinessKey;
            return this;
        }

        public boolean isNeedStartUsers() {
            return needStartUsers;
        }

        public ProcessDefinitionWrapperConfig setNeedStartUsers(boolean needStartUsers) {
            this.needStartUsers = needStartUsers;
            return this;
        }

        public boolean isNeedStartGroups() {
            return needStartGroups;
        }

        public ProcessDefinitionWrapperConfig setNeedStartGroups(boolean needStartGroups) {
            this.needStartGroups = needStartGroups;
            return this;
        }
    }
}
