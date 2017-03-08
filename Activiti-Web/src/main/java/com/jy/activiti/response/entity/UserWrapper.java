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

    public static class UserWrapperConfig extends BaseWrapper.Config {

        private boolean needUsername;
        private boolean needUserGroups;

        public boolean isNeedUsername() {
            return needUsername;
        }

        public UserWrapperConfig setNeedUsername(boolean needUsername) {
            this.needUsername = needUsername;
            return this;
        }

        public boolean isNeedUserGroups() {
            return needUserGroups;
        }

        public UserWrapperConfig setNeedUserGroups(boolean needUserGroups) {
            this.needUserGroups = needUserGroups;
            return this;
        }
    }
}
