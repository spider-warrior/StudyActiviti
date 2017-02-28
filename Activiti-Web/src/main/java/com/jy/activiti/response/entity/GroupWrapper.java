package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupWrapper extends BaseWrapper {

    @JsonProperty("name")
    private String name;

    @JsonProperty("groupType")
    private String groupType;

    public String getName() {
        return name;
    }

    public GroupWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroupType() {
        return groupType;
    }

    public GroupWrapper setGroupType(String groupType) {
        this.groupType = groupType;
        return this;
    }

    @Override
    public String toString() {
        return "GroupWrapper{" +
                "name='" + name + '\'' +
                ", groupType=" + groupType +
                "} " + super.toString();
    }
}
