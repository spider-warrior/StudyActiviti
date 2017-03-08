package com.jy.activiti.response.service;

import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.response.entity.HistoricProcessInstanceWrapper;
import com.jy.activiti.response.entity.UserWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoricProcessInstanceWrapperBuilder {

    private static final HistoricProcessInstanceWrapper empty = new HistoricProcessInstanceWrapper();

    @Autowired
    private IdentityService identityService;
    @Autowired
    private UserWrapperBuilder userWrapperBuilder;
    @Autowired
    private ContextHelper contextHelper;

    public HistoricProcessInstanceWrapper buildHistoricProcessInstanceWrapper(HistoricProcessInstance historicProcessInstance, HistoricProcessInstanceWrapper.HistoricProcessInstanceWrapperConfig config) {
        if (historicProcessInstance == null) {
            return empty;
        }
        HistoricProcessInstanceWrapper historicProcessInstanceWrapper = new HistoricProcessInstanceWrapper();
        if (config.isNeedId()) {
            historicProcessInstanceWrapper.setId(historicProcessInstance.getId());
        }
        if (config.isNeedBusinessKey()) {
            historicProcessInstanceWrapper.setBusinessKey(historicProcessInstance.getBusinessKey());
        }
        if (config.isNeedProcessDefinitionId()) {
            historicProcessInstanceWrapper.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        }
        if (config.isNeedStartTime()) {
            historicProcessInstanceWrapper.setStartTime(TimeUtil.formatYYYYMMMDDHHMMSS(historicProcessInstance.getStartTime()));
        }
        if (config.isNeedEndTime()) {
            historicProcessInstanceWrapper.setEndTime(TimeUtil.formatYYYYMMMDDHHMMSS(historicProcessInstance.getEndTime()));
        }
        if (config.isNeedStarter()) {
            UserWrapper.UserWrapperConfig userWrapperConfig = new UserWrapper.UserWrapperConfig();
            historicProcessInstanceWrapper.setStarter(userWrapperBuilder.buildUserWrapper(identityService.createUserQuery().userId(historicProcessInstance.getStartUserId()).singleResult(), userWrapperConfig));
        }

        return historicProcessInstanceWrapper;
    }
}
