package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupWrapper extends BaseWrapper {

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("groupType")
    private String groupType;

    public String getGroupName() {
        return groupName;
    }

    public GroupWrapper setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getGroupType() {
        return groupType;
    }

    public GroupWrapper setGroupType(String groupType) {
        this.groupType = groupType;
        return this;
    }

    public static class GroupWrapperConfig extends BaseWrapper.Config {
        private boolean needGroupName;
        private boolean needGroupType;

        public boolean isNeedGroupName() {
            return needGroupName;
        }

        public GroupWrapperConfig setNeedGroupName(boolean needGroupName) {
            this.needGroupName = needGroupName;
            return this;
        }

        public boolean isNeedGroupType() {
            return needGroupType;
        }

        public GroupWrapperConfig setNeedGroupType(boolean needGroupType) {
            this.needGroupType = needGroupType;
            return this;
        }
    }
}
