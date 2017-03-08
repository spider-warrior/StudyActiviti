package com.jy.activiti.response.service;

import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.response.entity.CommentWrapper;
import com.jy.activiti.response.entity.TaskWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
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
    private VariableInstanceWrapperBuilder variableInstanceWrapperBuilder;
    @Autowired
    private CommentWrapperBuilder commentWrapperBuilder;

    private static final TaskWrapper empty = new TaskWrapper();

    public TaskWrapper buildTaskWrapper(Task task, boolean needVariables) {
        if (task == null) {
            return empty;
        }
        TaskWrapper taskWrapper = new TaskWrapper();
        taskWrapper.setId(task.getId());
        taskWrapper.setName(task.getName());
        taskWrapper.setTaskDefinitionKey(task.getTaskDefinitionKey());
        taskWrapper.setCategory(task.getCategory());
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        taskWrapper.setProcessDefinitionWrapper(processDefinitionWrapperBuilder.buildProcessDefinitionWrapper(processDefinition));
        taskWrapper.setExecutionId(task.getExecutionId());
        taskWrapper.setOwner(StringUtil.isEmpty(task.getOwner()) ? null : userWrapperBuilder.buildUserWrapper(identityService.createUserQuery().userId(task.getOwner()).singleResult()));
        taskWrapper.setAssignee(task.getAssignee());
        taskWrapper.setCreateTime(TimeUtil.formatYYYYMMMDDHHMMSS(task.getCreateTime()));
        taskWrapper.setDescription(task.getDescription());
        if (needVariables) {
            Map<String,Object> variables = taskService.getVariables(task.getId());
            taskWrapper.setVariables(variables);
        }
        List<Comment> commentList = taskService.getTaskComments(task.getId());
        CommentEntity entity = new CommentEntity();
        entity.setId("1000");
        entity.setUserId("amen");
        entity.setTime(new Date());
        entity.setFullMessage("OK OK OK");
        commentList.add(entity);
        if (commentList != null && !commentList.isEmpty()) {
            List<CommentWrapper> commentWrapperList = new ArrayList<>(commentList.size());
            CommentWrapper.CommentWrapperConfig config = new CommentWrapper.CommentWrapperConfig();
            commentList.forEach(comment -> commentWrapperList.add(commentWrapperBuilder.buildCommentWrapper(comment, config)));
            taskWrapper.setComments(commentWrapperList);
        }
        return taskWrapper;
    }
}
