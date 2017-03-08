package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricProcessInstanceWrapper extends BaseWrapper {

    @JsonProperty("businessKey")
    private String businessKey;

    @JsonProperty("processDefinitionId")
    private String processDefinitionId;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("starter")
    private UserWrapper starter;

    public String getBusinessKey() {
        return businessKey;
    }

    public HistoricProcessInstanceWrapper setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public HistoricProcessInstanceWrapper setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public HistoricProcessInstanceWrapper setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public HistoricProcessInstanceWrapper setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public UserWrapper getStarter() {
        return starter;
    }

    public HistoricProcessInstanceWrapper setStarter(UserWrapper starter) {
        this.starter = starter;
        return this;
    }

    public static class HistoricProcessInstanceWrapperConfig extends BaseWrapper.Config {

        private boolean needBusinessKey = true;
        private boolean needProcessDefinitionId = true;
        private boolean needStartTime = true;
        private boolean needEndTime = true;
        private boolean needStarter = true;

        public boolean isNeedBusinessKey() {
            return needBusinessKey;
        }

        public HistoricProcessInstanceWrapperConfig setNeedBusinessKey(boolean needBusinessKey) {
            this.needBusinessKey = needBusinessKey;
            return this;
        }

        public boolean isNeedProcessDefinitionId() {
            return needProcessDefinitionId;
        }

        public HistoricProcessInstanceWrapperConfig setNeedProcessDefinitionId(boolean needProcessDefinitionId) {
            this.needProcessDefinitionId = needProcessDefinitionId;
            return this;
        }

        public boolean isNeedStartTime() {
            return needStartTime;
        }

        public HistoricProcessInstanceWrapperConfig setNeedStartTime(boolean needStartTime) {
            this.needStartTime = needStartTime;
            return this;
        }

        public boolean isNeedEndTime() {
            return needEndTime;
        }

        public HistoricProcessInstanceWrapperConfig setNeedEndTime(boolean needEndTime) {
            this.needEndTime = needEndTime;
            return this;
        }

        public boolean isNeedStarter() {
            return needStarter;
        }

        public HistoricProcessInstanceWrapperConfig setNeedStarter(boolean needStarter) {
            this.needStarter = needStarter;
            return this;
        }
    }
}
