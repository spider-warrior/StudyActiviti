package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDefinitionWrapper extends BaseWrapper{

    @JsonProperty("name")
    private String name;

    @JsonProperty("businessKey")
    private String businessKey;

    @JsonProperty("owners")
    List<UserWrapper> owners;


    public String getName() {
        return name;
    }

    public ProcessDefinitionWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public ProcessDefinitionWrapper setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public List<UserWrapper> getOwners() {
        return owners;
    }

    public ProcessDefinitionWrapper setOwners(List<UserWrapper> owners) {
        this.owners = owners;
        return this;
    }
}
