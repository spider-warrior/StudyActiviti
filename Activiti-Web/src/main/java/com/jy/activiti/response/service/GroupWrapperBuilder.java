package com.jy.activiti.response.service;

import com.jy.activiti.response.entity.GroupWrapper;
import org.activiti.engine.identity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupWrapperBuilder {

    public static final GroupWrapper empty = new GroupWrapper();

    public GroupWrapper buildGroupWrapper(Group group) {
        if (group == null) {
            return empty;
        }
        GroupWrapper groupWrapper = new GroupWrapper();
        groupWrapper.setId(group.getId());
        groupWrapper.setName(group.getName());
        groupWrapper.setGroupType(group.getType());
        return groupWrapper;
    }
}
