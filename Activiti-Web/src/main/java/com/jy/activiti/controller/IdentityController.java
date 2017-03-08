package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.response.entity.GroupWrapper;
import com.jy.activiti.response.entity.UserWrapper;
import com.jy.activiti.response.service.GroupWrapperBuilder;
import com.jy.activiti.response.service.UserWrapperBuilder;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        } else {
            List<UserWrapper> userWrapperList = new ArrayList<>();
            UserWrapper.UserWrapperConfig userWrapperConfig = new UserWrapper.UserWrapperConfig();
            users.forEach(user -> userWrapperList.add(userWrapperBuilder.buildUserWrapper(user, userWrapperConfig)));
            result.put("users", userWrapperList);
        }
        return success(result);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Object addUser(@RequestBody Map<String, String> param) {
        String id = param.get("id");
        String password = param.get("password");
        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(password)) {
            return failOnParamInvalid(null);
        }
        User user = identityService.newUser(id);
        user.setPassword(password);
        identityService.saveUser(user);
        return success();
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public Object deleteUser(@PathVariable("userId") String userId) {
        identityService.deleteUser(userId);
        return success();
    }


    @RequestMapping(value = "/groups", method = {RequestMethod.GET, RequestMethod.POST})
    public Object groupList(@RequestBody(required = false) Map<String, String> param) {
        Map<String, Object> result = new HashMap<>();
        List<Group> groupList = identityService.createGroupQuery().list();
        if (groupList == null || groupList.size() == 0) {
            result.put("groups", Collections.emptyList());
        } else {
            List<GroupWrapper> groupWrapperList = new ArrayList<>();
            GroupWrapper.GroupWrapperConfig config = new GroupWrapper.GroupWrapperConfig();
            groupList.forEach(group -> groupWrapperList.add(groupWrapperBuilder.buildGroupWrapper(group, config)));
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

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.DELETE)
    public Object deleteGroup(@PathVariable("groupId") String groupId) {
        identityService.deleteGroup(groupId);
        return success();
    }

    @RequestMapping(value = "/group/membership", method = RequestMethod.POST)
    public Object addGroupMember(@RequestBody Map<String, String> param) {
        String userId = param.get("userId");
        String groupId = param.get("groupId");
        if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(groupId)) {
            return failOnParamInvalid(null);
        }
        long groupCount = identityService.createGroupQuery().groupId(groupId).count();
        if (groupCount == 0) {
            return failSourceNotFound(ResourcesType.GROUP.getValue());
        }
        long userCount = identityService.createUserQuery().userId(userId).count();
        if (userCount == 0) {
            return failSourceNotFound(ResourcesType.USER.getValue());
        }
        identityService.createMembership(userId, groupId);
        return success();
    }

    @RequestMapping(value = "/group/membership", method = RequestMethod.DELETE)
    public Object deleteGroupMember(@RequestBody Map<String, String> param) {
        String userId = param.get("userId");
        String groupId = param.get("groupId");
        if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(groupId)) {
            return failOnParamInvalid(null);
        }
        identityService.deleteMembership(userId, groupId);
        return success();
    }

}
