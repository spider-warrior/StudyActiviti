package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.common.enums.ResponseCode;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.service.exception.ExceptionCode;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredLogin
@RequestMapping("/student-ask-for-leave")
@RestController
public class StudentAskForLeaveController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private ContextHelper contextHelper;

    /**
     * {
     * time: xxx, (时间，单位： 天)
     * reason: xxx（请假事由）
     * }
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object add(@RequestBody Map<String, Object> param) {

        Object timeStr = param.get("time");
        Object reason = param.get("reason");
        String processDefinitionId = (String)param.get("processDefinitionId");
        //参数异常
        if (timeStr == null || reason == null || processDefinitionId == null) {
            return failOnParamInvalid(null);
        }
        //流程是否存在
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        if (pd == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION.getValue());
        }
        User user = contextHelper.getCurrentUser();
        if (user == null) {
            return fail();
        }
        //是否有权限启动流程
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().startableByUser(user.getId().toString()).processDefinitionId(pd.getId()).singleResult();
        boolean hasAuth = false;
        if (processDefinition != null) {
            hasAuth = true;
        }
        if (hasAuth) {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("student-ask-for-leave", param);
            return success();
        } else {
            return failOnRequestNotAllow();
        }

    }


}
