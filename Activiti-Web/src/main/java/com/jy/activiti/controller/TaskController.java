package com.jy.activiti.controller;


import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.response.entity.TaskWrapper;
import com.jy.activiti.response.service.TaskWrapperBuilder;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredLogin
@RequestMapping("/task")
@RestController
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskWrapperBuilder taskWrapperBuilder;

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public Object userTaskList(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return failSourceNotFound(ResourcesType.USER_TASK.getValue());
        }
        TaskWrapper.TaskWrapperConfig taskWrapperConfig = new TaskWrapper.TaskWrapperConfig();
        TaskWrapper taskWrapper = taskWrapperBuilder.buildTaskWrapper(task, taskWrapperConfig);
        Map<String, Object> result = new HashMap<>();
        result.put("task", taskWrapper);
        return success(result);
    }
}
