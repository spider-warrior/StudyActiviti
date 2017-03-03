package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.response.entity.TaskWrapper;
import com.jy.activiti.response.service.TaskWrapperBuilder;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredLogin
@RequestMapping("/user")
@RestController
public class UserController extends BaseController{

    @Autowired
    private ContextHelper contextHelper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskWrapperBuilder taskWrapperBuilder;

    @RequestMapping("/task/list")
    public Object userTaskList(@RequestBody(required = false) Map<String, String> param) {
        User user = contextHelper.getCurrentUser();
        List<Task> taskEntityList = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId().toString()).list();
        List<TaskWrapper> taskWrapperList = new ArrayList<>(taskEntityList.size());
        taskEntityList.forEach(task -> taskWrapperList.add(taskWrapperBuilder.buildTaskWrapper(task)));
        Map<String, Object> result = new HashMap<>();
        result.put("tasks", taskWrapperList);
        return success(result);
    }

}
