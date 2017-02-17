package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.common.enums.ResponseCode;
import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.response.entity.ProcessDefinitionWrapper;
import com.jy.activiti.response.service.ProcessDefinitionWrapperBuilder;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/pdm")
@RestController
public class ProcessDefinitionController extends BaseController{

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessDefinitionWrapperBuilder processDefinitionWrapperBuilder;

    @RequiredLogin
    @RequestMapping("/list")
    public Object queryAllProcessDefinition(@RequestBody(required = false) Map<String, Object> param) {
        Map<String, Object> result = new HashMap<>();
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
        List<ProcessDefinitionWrapper> processDefinitionWrappers = new ArrayList<>(processDefinitionList.size());
        processDefinitionList.forEach(p -> processDefinitionWrappers.add(processDefinitionWrapperBuilder.buildProcessDefinitionWrapper(p)));
        result.put("processes", processDefinitionWrappers);
        return success(result);
    }

    @RequiredLogin
    @RequestMapping("/user/add/auth")
    public Object userAddPdAuth(@RequestBody(required = false) Map<String, String> param) {
        String userId = param.get("username");
        String pdid = param.get("pdid");

        if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(pdid)) {
            return fail(ResponseCode.REQUEST_PARAM_ERROR.getValue());
        }

        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) {
            return fail(ResponseCode.REQUEST_SOURCE_NOT_FOUND.getValue(), ResourcesType.USER.getValue());
        }
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
        if (pd == null) {
            return fail(ResponseCode.REQUEST_SOURCE_NOT_FOUND.getValue(), ResourcesType.PROCESSDEFINITION.getValue());
        }
        repositoryService.addCandidateStarterUser(pdid, userId);
        return success();
    }

}
