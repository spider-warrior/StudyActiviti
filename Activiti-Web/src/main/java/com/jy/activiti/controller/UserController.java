package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.response.entity.TaskWrapper;
import com.jy.activiti.response.service.TaskWrapperBuilder;
import com.jy.activiti.service.exception.ExceptionCode;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredLogin
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private ContextHelper contextHelper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskWrapperBuilder taskWrapperBuilder;

    @RequestMapping("/task/available/list")
    public Object userTaskList(@RequestBody(required = false) Map<String, String> param) {
        User user = contextHelper.getCurrentUser();
        List<Task> taskEntityList = taskService.createTaskQuery().taskUnassigned().taskCandidateUser(user.getId()).list();
        List<TaskWrapper> taskWrapperList = new ArrayList<>(taskEntityList.size());
        TaskWrapper.TaskWrapperConfig taskWrapperConfig = new TaskWrapper.TaskWrapperConfig();
        taskWrapperConfig.setNeedVariables(false);
        taskEntityList.forEach(task -> taskWrapperList.add(taskWrapperBuilder.buildTaskWrapper(task, taskWrapperConfig)));
        Map<String, Object> result = new HashMap<>();
        result.put("tasks", taskWrapperList);
        return success(result);
    }

    @RequestMapping("/task/claimed/list")
    public Object userClaimedTaskList(@RequestBody(required = false) Map<String, String> param) {
        User user = contextHelper.getCurrentUser();
        List<Task> taskEntityList = taskService.createTaskQuery().taskAssignee(user.getId()).list();
        List<TaskWrapper> taskWrapperList = new ArrayList<>(taskEntityList.size());
        TaskWrapper.TaskWrapperConfig taskWrapperConfig = new TaskWrapper.TaskWrapperConfig();
        taskWrapperConfig.setNeedVariables(false);
        taskEntityList.forEach(task -> taskWrapperList.add(taskWrapperBuilder.buildTaskWrapper(task, taskWrapperConfig)));
        Map<String, Object> result = new HashMap<>();
        result.put("tasks", taskWrapperList);
        return success(result);
    }

    @RequestMapping("/task/claime/{taskId}")
    public Object userClaimeTask(@PathVariable("taskId") String taskId) {
        User user = contextHelper.getCurrentUser();
        Task task = taskService.createTaskQuery().taskUnassigned().taskId(taskId).singleResult();
        if (task == null) {
            return failSourceNotFound(ResourcesType.USER_TASK.getValue());
        }
        taskService.claim(taskId, user.getId());
        return success();
    }

    @RequestMapping(value = "/task/audit/student-ask-for-leave/agree", method = RequestMethod.POST)
    public Object teacherAuditStudentAskForLeaveAgree(@RequestBody Map<String, String> param) {
        String taskId = param.get("taskId");
        String commnect = param.get("comment");
        User user = contextHelper.getCurrentUser();
        Task task = taskService.createTaskQuery().taskAssignee(user.getId()).taskId(taskId).singleResult();
        if (task == null) {
            return failSourceNotFound(ResourcesType.USER_TASK.getValue());
        }
        if (!user.getId().equals(task.getAssignee())) {
            return failOnRequestNotAllow();
        }
        taskService.addComment(taskId, task.getProcessInstanceId(), commnect);
        taskService.complete(taskId);
        return success();
    }


}
