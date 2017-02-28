package com.jy.activiti.response.service;

import com.jy.activiti.entity.User;
import com.jy.activiti.response.entity.GroupWrapper;
import com.jy.activiti.response.entity.UserWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserWrapperBuilder {

    private static final UserWrapper empty = new UserWrapper();

    @Autowired
    private IdentityService identityService;
    @Autowired
    private GroupWrapperBuilder groupWrapperBuilder;

    public UserWrapper buildUserWrapper(User user) {
        if (user == null) {
            return empty;
        }
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setId(user.getId().toString());
        userWrapper.setUsername(user.getUsername());
        return userWrapper;
    }

    public UserWrapper buildUserWrapper(org.activiti.engine.identity.User user) {
        if (user == null) {
            return empty;
        }
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setId(user.getId().toString());
        List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();
        if (groupList !=null && groupList.size() > 0) {
            List<GroupWrapper> groupWrapperList = new ArrayList<>();
            groupList.forEach(group -> groupWrapperList.add(groupWrapperBuilder.buildGroupWrapper(group)));
            userWrapper.setUserGroups(groupWrapperList);
        }
        return userWrapper;
    }

}
