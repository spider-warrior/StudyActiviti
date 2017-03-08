package com.jy.activiti.response.service;

import com.jy.activiti.response.entity.GroupWrapper;
import org.activiti.engine.identity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupWrapperBuilder {

    public static final GroupWrapper empty = new GroupWrapper();

    public GroupWrapper buildGroupWrapper(Group group, GroupWrapper.GroupWrapperConfig config) {
        if (group == null) {
            return empty;
        }
        GroupWrapper groupWrapper = new GroupWrapper();
        if (config.isNeedId()) {
            groupWrapper.setId(group.getId());
        }
        if (config.isNeedGroupName()) {
            groupWrapper.setGroupName(group.getName());
        }
        if (config.isNeedGroupType()) {
            groupWrapper.setGroupType(group.getType());
        }
        return groupWrapper;
    }
}
