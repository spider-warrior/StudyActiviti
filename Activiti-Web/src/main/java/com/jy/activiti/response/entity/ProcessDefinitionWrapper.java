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

    @JsonProperty("candidateUsers")
    List<UserWrapper> candidateUsers;


    @JsonProperty("candidateGroups")
    List<GroupWrapper> candidateGroups;

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

    public List<UserWrapper> getCandidateUsers() {
        return candidateUsers;
    }

    public ProcessDefinitionWrapper setCandidateUsers(List<UserWrapper> candidateUsers) {
        this.candidateUsers = candidateUsers;
        return this;
    }

    public List<GroupWrapper> getCandidateGroups() {
        return candidateGroups;
    }

    public ProcessDefinitionWrapper setCandidateGroups(List<GroupWrapper> candidateGroups) {
        this.candidateGroups = candidateGroups;
        return this;
    }
}
