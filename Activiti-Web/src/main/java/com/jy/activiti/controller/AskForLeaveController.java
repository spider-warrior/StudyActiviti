package com.jy.activiti.controller;

import com.jy.activiti.entity.User;
import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResponseCode;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.service.exception.ExceptionCode;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredLogin
@RequestMapping("askforleave")
@RestController
public class AskForLeaveController extends BaseController{

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
     *     time: xxx, (时间，单位： 天)
     *     reason: xxx（请假事由）
     * }
     *
     * */
    @RequestMapping(method = RequestMethod.POST)
    public Object add(@RequestBody Map<String, Object> param) {

        Object timeStr = param.get("time");
        Object reason = param.get("reason");
        //参数异常
        if (timeStr == null || reason == null) {
            return fail(ExceptionCode.PARAM_INVALID.getValue(), null);
        }
        //流程是否存在
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKeyLike("askForLeaveProcess").singleResult();
        if (pd == null) {
            return fail(ExceptionCode.SERVER_INTERNAL_EXCEPTION.getValue(), "流程不存在");
        }
        User user = contextHelper.getCurrentUser();
        if (user == null) {
            return fail();
        }
        //是否有权限启动流程
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().startableByUser(user.getId().toString()).list();

        boolean hasAuth = false;
        for (ProcessDefinition d : processDefinitionList) {
            if (pd.getId().equals(d.getId())) {
                hasAuth = true;
            }
        }
        if (hasAuth) {
            runtimeService.startProcessInstanceByKey("askForLeaveProcess", param);
            return success();
        }
        else {
            return fail(ResponseCode.REQUEST_NOT_ALLOWED.getValue());
        }

    }


}
