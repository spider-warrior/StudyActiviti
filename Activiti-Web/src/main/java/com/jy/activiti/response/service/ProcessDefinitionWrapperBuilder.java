package com.jy.activiti.response.service;

import com.jy.activiti.response.entity.GroupWrapper;
import com.jy.activiti.response.entity.ProcessDefinitionWrapper;
import com.jy.activiti.response.entity.UserWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessDefinitionWrapperBuilder {


    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private UserWrapperBuilder userWrapperBuilder;
    @Autowired
    private GroupWrapperBuilder groupWrapperBuilder;

    private static final ProcessDefinitionWrapper empty = new ProcessDefinitionWrapper();

    public ProcessDefinitionWrapper buildProcessDefinitionWrapper(ProcessDefinition processDefinition, ProcessDefinitionWrapper.ProcessDefinitionWrapperConfig config) {
        if (processDefinition == null) {
            return empty;
        }
        ProcessDefinitionWrapper processDefinitionWrapper = new ProcessDefinitionWrapper();
        if (config.isNeedId()) {
            processDefinitionWrapper.setId(processDefinition.getId());
        }
        if (config.isNeedBusinessKey()) {
            processDefinitionWrapper.setBusinessKey(processDefinition.getKey());
        }
        if (config.isProcessDefinitionName()) {
            processDefinitionWrapper.setProcessDefinitionName(processDefinition.getName());
        }
        if (config.isNeedStartUsers()) {
            List<User> activitiUsers = identityService.createUserQuery().potentialStarter(processDefinition.getId()).list();
            if (activitiUsers != null && activitiUsers.size() > 0) {
                List<UserWrapper> userWrappers = new ArrayList<>();
                UserWrapper.UserWrapperConfig userWrapperConfig = new UserWrapper.UserWrapperConfig();
                activitiUsers.forEach(user -> userWrappers.add(userWrapperBuilder.buildUserWrapper(user, userWrapperConfig)));
                processDefinitionWrapper.setStartUsers(userWrappers);
            }
        }
        if (config.isNeedStartGroups()) {
            List<Group> activitiGroups = identityService.createGroupQuery().potentialStarter(processDefinition.getId()).list();
            if (activitiGroups != null && activitiGroups.size() > 0) {
                List<GroupWrapper> groupWrappers = new ArrayList<>();
                GroupWrapper.GroupWrapperConfig groupWrapperConfig = new GroupWrapper.GroupWrapperConfig();
                activitiGroups.forEach(group -> groupWrappers.add(groupWrapperBuilder.buildGroupWrapper(group, groupWrapperConfig)));
                processDefinitionWrapper.setStartGroups(groupWrappers);
            }
        }

        return processDefinitionWrapper;
    }
}
