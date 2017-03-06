package com.jy.activiti.response.service;

import com.jy.activiti.common.util.StringUtil;
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
        taskWrapper.setCategory(task.getCategory());
        taskWrapper.setProcessDefinitionId(task.getProcessDefinitionId());
        taskWrapper.setExecutionId(task.getExecutionId());
        taskWrapper.setOwner(StringUtil.isEmpty(task.getOwner()) ? null : userWrapperBuilder.buildUserWrapper(identityService.createUserQuery().userId(task.getOwner()).singleResult()));
        taskWrapper.setAssignee(task.getAssignee());
        taskWrapper.setCreateTime(TimeUtil.formatYYYYMMMDDHHMMSS(task.getCreateTime()));
        taskWrapper.setDescription(task.getDescription());
        return taskWrapper;
    }
}
