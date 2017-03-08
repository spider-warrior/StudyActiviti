package com.jy.activiti.response.service;

import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.response.entity.CommentWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommentWrapperBuilder {

    private static final CommentWrapper empty = new CommentWrapper();
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private UserWrapperBuilder userWrapperBuilder;


    public CommentWrapper buildCommentWrapper(Comment comment, CommentWrapper.CommentWrapperConfig config) {
        if (comment == null) {
            return empty;
        }
        CommentWrapper commentWrapper = new CommentWrapper();
        if (config.isNeedId()) {
            commentWrapper.setId(comment.getId());
        }
        if (config.isNeedCommentUser()) {
            commentWrapper.setCommentUser(userWrapperBuilder.buildUserWrapper(identityService.createUserQuery().userId(comment.getUserId()).singleResult()));
        }
        if (config.isNeedType()) {
            commentWrapper.setType(comment.getType());
        }
        if (config.isNeedCreateTime()) {
            commentWrapper.setCreateTime(TimeUtil.formatYYYYMMMDDHHMMSS(comment.getTime()));
        }
        if (config.isNeedTaskId()) {
            commentWrapper.setTaskId(comment.getTaskId());
        }
        if (config.isNeedProcessInstanceId()) {
            commentWrapper.setProcessInstanceId(comment.getProcessInstanceId());
        }
        if (config.isNeedContent()) {
            commentWrapper.setContent(comment.getFullMessage());
        }
        return commentWrapper;
    }
}
