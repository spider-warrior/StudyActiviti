package com.jy.activiti.response.service;

import com.jy.activiti.entity.User;
import com.jy.activiti.response.entity.UserWrapper;
import org.springframework.stereotype.Component;

@Component
public class UserWrapperBuilder {

    private static final UserWrapper empty = new UserWrapper();

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
        return userWrapper;
    }

}
