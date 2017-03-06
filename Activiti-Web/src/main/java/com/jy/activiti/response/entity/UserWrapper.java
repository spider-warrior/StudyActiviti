package com.jy.activiti.response.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWrapper extends BaseWrapper {

    @JsonProperty("username")
    private String username;

    @JsonProperty("userGroups")
    private List<GroupWrapper> userGroups;

    public String getUsername() {
        return username;
    }

    public UserWrapper setUsername(String username) {
        this.username = username;
        return this;
    }

    public List<GroupWrapper> getUserGroups() {
        return userGroups;
    }

    public UserWrapper setUserGroups(List<GroupWrapper> userGroups) {
        this.userGroups = userGroups;
        return this;
    }

    @Override
    public String toString() {
        return "UserWrapper{" +
                "username='" + username + '\'' +
                ", userGroups=" + userGroups +
                "} " + super.toString();
    }
}
