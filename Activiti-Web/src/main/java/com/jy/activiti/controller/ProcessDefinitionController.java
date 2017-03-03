package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.common.enums.ResponseCode;
import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.response.entity.ProcessDefinitionWrapper;
import com.jy.activiti.response.service.ProcessDefinitionWrapperBuilder;
import com.jy.activiti.service.exception.ExceptionCode;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
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

    @RequiredLogin
    @RequestMapping("/user/delete/auth")
    public Object userDeletePdAuth(@RequestBody(required = false) Map<String, String> param) {
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
        repositoryService.deleteCandidateStarterUser(pdid, userId);
        return success();
    }

    @RequiredLogin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deletePd(@PathVariable("id") String pdId) {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).singleResult();
        if (pd == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION.getValue());
        }
        repositoryService.deleteDeployment(pd.getDeploymentId());
        return success();
    }

    @RequiredLogin
    @RequestMapping(value = "/{businessKey}/img", method = RequestMethod.GET)
    public Object pdImg(@PathVariable("businessKey") String businessKey) throws IOException {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(businessKey).singleResult();
        if (pd == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION.getValue());
        }
        InputStream inputStream = repositoryService.getProcessDiagram(pd.getId());
        if (inputStream == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION_IMG.getValue());
        }
        byte[] img = new byte[inputStream.available()];
        inputStream.read(img);
        return img;
    }

}
