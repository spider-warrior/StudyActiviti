package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.response.entity.GroupWrapper;
import com.jy.activiti.response.entity.UserWrapper;
import com.jy.activiti.response.service.GroupWrapperBuilder;
import com.jy.activiti.response.service.UserWrapperBuilder;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    private GroupWrapperBuilder groupWrapperBuilder;

    @RequestMapping(value = "/users", method = {RequestMethod.GET, RequestMethod.POST})
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


    @RequestMapping(value = "/groups", method = {RequestMethod.GET, RequestMethod.POST})
    public Object groupList(@RequestBody(required = false) Map<String, String> param) {
        Map<String, Object> result = new HashMap<>();
        List<Group> groupList = identityService.createGroupQuery().list();
        if (groupList == null || groupList.size() == 0) {
            result.put("groups", Collections.emptyList());
        }
        else {
            List<GroupWrapper> groupWrapperList = new ArrayList<>();
            groupList.forEach(group -> groupWrapperList.add(groupWrapperBuilder.buildGroupWrapper(group)));
            result.put("groups", groupWrapperList);
        }
        return success(result);
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public Object createGroup(@RequestBody Map<String, String> param) {
        String id = param.get("id");
        String name = param.get("name");
        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(name)) {
            return failOnParamInvalid(null);
        }
        Group group = identityService.newGroup(id);
        group.setName(name);
        identityService.saveGroup(group);
        return success();
    }

}
