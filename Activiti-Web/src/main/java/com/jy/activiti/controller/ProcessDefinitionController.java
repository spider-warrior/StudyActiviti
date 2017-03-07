package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.common.enums.ResponseCode;
import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.response.entity.ProcessDefinitionWrapper;
import com.jy.activiti.response.entity.TaskWrapper;
import com.jy.activiti.response.service.ProcessDefinitionWrapperBuilder;
import com.jy.activiti.response.service.TaskWrapperBuilder;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequestMapping("/processdefinition")
@RestController
public class ProcessDefinitionController extends BaseController {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessDefinitionWrapperBuilder processDefinitionWrapperBuilder;
    @Autowired
    private TaskWrapperBuilder taskWrapperBuilder;

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
    @RequestMapping("/group/add/auth")
    public Object groupAddPdAuth(@RequestBody Map<String, String> param) {
        String groupId = param.get("groupId");
        String pdid = param.get("pdid");

        if (StringUtil.isEmpty(groupId) || StringUtil.isEmpty(pdid)) {
            return fail(ResponseCode.REQUEST_PARAM_ERROR.getValue());
        }

        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) {
            return fail(ResponseCode.REQUEST_SOURCE_NOT_FOUND.getValue(), ResourcesType.GROUP.getValue());
        }
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
        if (pd == null) {
            return fail(ResponseCode.REQUEST_SOURCE_NOT_FOUND.getValue(), ResourcesType.PROCESSDEFINITION.getValue());
        }
        repositoryService.addCandidateStarterGroup(pdid, groupId);
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
    @RequestMapping("/group/delete/auth")
    public Object groupDeletePdAuth(@RequestBody(required = false) Map<String, String> param) {
        String groupId = param.get("groupId");
        String pdid = param.get("pdid");

        if (StringUtil.isEmpty(groupId) || StringUtil.isEmpty(pdid)) {
            return fail(ResponseCode.REQUEST_PARAM_ERROR.getValue());
        }

        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) {
            return fail(ResponseCode.REQUEST_SOURCE_NOT_FOUND.getValue(), ResourcesType.GROUP.getValue());
        }
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
        if (pd == null) {
            return fail(ResponseCode.REQUEST_SOURCE_NOT_FOUND.getValue(), ResourcesType.PROCESSDEFINITION.getValue());
        }
        repositoryService.deleteCandidateStarterGroup(pdid, groupId);
        return success();
    }


    @RequiredLogin
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deletePd(@PathVariable("id") String pdId) {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).singleResult();
        if (pd == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION.getValue());
        }
        repositoryService.deleteDeployment(pd.getDeploymentId(), true);
        return success();
    }

    @RequiredLogin
    @RequestMapping(value = "/{processDefinitionId}/image", method = RequestMethod.GET)
    public Object pdImg(@PathVariable("processDefinitionId") String processDefinitionId) throws IOException {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
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

    @RequiredLogin
    @RequestMapping(value = "/{id}/usertask", method = RequestMethod.GET)
    public Object processDefinitionUserTask(@PathVariable("id") String processDefinitionId) throws IOException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        if (processDefinition == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION.getValue());
        }
        Map<String, Object> result = new HashMap<>();
        List<Task> taskList = taskService.createTaskQuery().processDefinitionId(processDefinitionId).list();
        if (taskList == null || taskList.size() == 0) {
            result.put("tasks", Collections.emptyList());
        }
        else {
            List<TaskWrapper> taskWrapperList = new ArrayList<>(taskList.size());
            taskList.forEach(task -> taskWrapperList.add(taskWrapperBuilder.buildTaskWrapper(task)));
            result.put("tasks", taskWrapperList);
        }
        return success(result);
    }

}
