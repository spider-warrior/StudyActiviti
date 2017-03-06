package com.jy.activiti.response.service;

import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.response.entity.TaskWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskWrapperBuilder {

    @Autowired
    private UserWrapperBuilder userWrapperBuilder;
    @Autowired
    private IdentityService identityService;

    private static final TaskWrapper empty = new TaskWrapper();

    public TaskWrapper buildTaskWrapper(Task task) {
        if (task == null) {
            return empty;
        }
        TaskWrapper taskWrapper = new TaskWrapper();
        taskWrapper.setId(task.getId());
        taskWrapper.setName(task.getName());
        taskWrapper.setCreateTime(TimeUtil.formatYYYYMMMDDHHMMSS(task.getCreateTime()));
        taskWrapper.setAssignee(task.getAssignee());
        taskWrapper.setCategory(task.getCategory());
        taskWrapper.setDescription(task.getDescription());
        taskWrapper.setExecutionId(task.getExecutionId());
        taskWrapper.setOwner(userWrapperBuilder.buildUserWrapper(identityService.createUserQuery().userId(task.getOwner()).singleResult()));
        taskWrapper.setProcessDefinitionId(task.getProcessDefinitionId());
        return taskWrapper;
    }
}
