package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.response.entity.UserWrapper;
import com.jy.activiti.response.service.UserWrapperBuilder;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredLogin
@RequestMapping("/identity")
@RestController
public class IdentityController extends BaseController {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private UserWrapperBuilder userWrapperBuilder;

    @RequestMapping("/users")
    public Object userList(@RequestBody(required = false) Map<String, String> param) {

        Map<String, Object> result = new HashMap<>();
        List<User> users = identityService.createUserQuery().list();
        if (users == null || users.size() == 0) {
            result.put("users", Collections.emptyList());
        }else {
            List<UserWrapper> userWrapperList = new ArrayList<>();
            users.forEach(user -> userWrapperList.add(userWrapperBuilder.buildUserWrapper(user)));
            result.put("users", userWrapperList);
        }
        return success(result);
    }


}
