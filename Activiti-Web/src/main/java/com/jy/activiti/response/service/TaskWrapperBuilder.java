package com.jy.activiti.response.service;

import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.response.entity.CommentWrapper;
import com.jy.activiti.response.entity.ProcessDefinitionWrapper;
import com.jy.activiti.response.entity.TaskWrapper;
import com.jy.activiti.response.entity.UserWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class TaskWrapperBuilder {

    @Autowired
    private UserWrapperBuilder userWrapperBuilder;
    @Autowired
    private ProcessDefinitionWrapperBuilder processDefinitionWrapperBuilder;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentWrapperBuilder commentWrapperBuilder;

    private static final TaskWrapper empty = new TaskWrapper();

    public TaskWrapper buildTaskWrapper(Task task, TaskWrapper.TaskWrapperConfig config) {
        if (task == null) {
            return empty;
        }
        TaskWrapper taskWrapper = new TaskWrapper();
        if (config.isNeedId()) {
            taskWrapper.setId(task.getId());
        }
        if (config.isNeedTaskName()) {
            taskWrapper.setTaskName(task.getName());
        }
        if (config.isNeedTaskDefinitionKey()) {
            taskWrapper.setTaskDefinitionKey(task.getTaskDefinitionKey());
        }
        if (config.isNeedCategory()) {
            taskWrapper.setCategory(task.getCategory());
        }
        if (config.isNeedProcessDefinitionWrapper()) {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
            ProcessDefinitionWrapper.ProcessDefinitionWrapperConfig processDefinitionWrapperConfig = new ProcessDefinitionWrapper.ProcessDefinitionWrapperConfig();
            taskWrapper.setProcessDefinitionWrapper(processDefinitionWrapperBuilder.buildProcessDefinitionWrapper(processDefinition, processDefinitionWrapperConfig));
        }
        if (config.isNeedExecutionId()) {
            taskWrapper.setExecutionId(task.getExecutionId());
        }
        if (config.isNeedOwner()) {
            UserWrapper.UserWrapperConfig userWrapperConfig = new UserWrapper.UserWrapperConfig();
            taskWrapper.setOwner(StringUtil.isEmpty(task.getOwner()) ? null : userWrapperBuilder.buildUserWrapper(identityService.createUserQuery().userId(task.getOwner()).singleResult(), userWrapperConfig));
        }
        if (config.isNeedAssignee()) {
            taskWrapper.setAssignee(task.getAssignee());
        }
        if (config.isNeedCreateTime()) {
            taskWrapper.setCreateTime(TimeUtil.formatYYYYMMMDDHHMMSS(task.getCreateTime()));
        }
        if (config.isNeedDescription()) {
            taskWrapper.setDescription(task.getDescription());
        }
        if (config.isNeedVariables()) {
            Map<String,Object> variables = taskService.getVariables(task.getId());
            taskWrapper.setVariables(variables);
        }
        if (config.isNeedComments()) {
            List<Comment> commentList = taskService.getTaskComments(task.getId());
            CommentEntity entity = new CommentEntity();
            entity.setId("1000");
            entity.setUserId("amen");
            entity.setTime(new Date());
            entity.setFullMessage("OK OK OK");
            commentList.add(entity);
            if (commentList != null && !commentList.isEmpty()) {
                List<CommentWrapper> commentWrapperList = new ArrayList<>(commentList.size());
                CommentWrapper.CommentWrapperConfig commentWrapperConfig = new CommentWrapper.CommentWrapperConfig();
                commentList.forEach(comment -> commentWrapperList.add(commentWrapperBuilder.buildCommentWrapper(comment, commentWrapperConfig)));
                taskWrapper.setComments(commentWrapperList);
            }
        }
        return taskWrapper;
    }
}
