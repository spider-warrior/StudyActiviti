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

    public ProcessDefinitionWrapper buildProcessDefinitionWrapper(ProcessDefinition processDefinition) {
        if (processDefinition == null) {
            return empty;
        }
        ProcessDefinitionWrapper processDefinitionWrapper = new ProcessDefinitionWrapper();
        processDefinitionWrapper.setId(processDefinition.getId());
        processDefinitionWrapper.setBusinessKey(processDefinition.getKey());
        processDefinitionWrapper.setName(processDefinition.getName());
        List<User> activitiUsers = identityService.createUserQuery().potentialStarter(processDefinition.getId()).list();
        if (activitiUsers != null && activitiUsers.size() > 0) {
            List<UserWrapper> userWrappers = new ArrayList<>();
            activitiUsers.forEach(user -> userWrappers.add(userWrapperBuilder.buildUserWrapper(user)));
            processDefinitionWrapper.setCandidateUsers(userWrappers);
        }
        List<Group> activitiGroups = identityService.createGroupQuery().potentialStarter(processDefinition.getId()).list();
        if (activitiGroups != null && activitiGroups.size() > 0) {
            List<GroupWrapper> groupWrappers = new ArrayList<>();
            activitiGroups.forEach(group -> groupWrappers.add(groupWrapperBuilder.buildGroupWrapper(group)));
            processDefinitionWrapper.setCandidateGroups(groupWrappers);
        }
        return processDefinitionWrapper;
    }
}
