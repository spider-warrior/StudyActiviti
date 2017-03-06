package com.jy.activiti.response.service;

import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.response.entity.HistoricProcessInstanceWrapper;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoricProcessInstanceWrapperBuilder {

    private static final HistoricProcessInstanceWrapper empty = new HistoricProcessInstanceWrapper();
    @Autowired
    private UserWrapperBuilder userWrapperBuilder;
    @Autowired
    private ContextHelper contextHelper;

    public HistoricProcessInstanceWrapper buildHistoricProcessInstanceWrapper(HistoricProcessInstance historicProcessInstance) {
        if (historicProcessInstance == null) {
            return empty;
        }
        HistoricProcessInstanceWrapper historicProcessInstanceWrapper = new HistoricProcessInstanceWrapper();
        historicProcessInstanceWrapper.setId(historicProcessInstance.getId());
        historicProcessInstanceWrapper.setBusinessKey(historicProcessInstance.getBusinessKey());
        historicProcessInstanceWrapper.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        historicProcessInstanceWrapper.setStartTime(TimeUtil.formatYYYYMMMDDHHMMSS(historicProcessInstance.getStartTime()));
        historicProcessInstanceWrapper.setEndTime(TimeUtil.formatYYYYMMMDDHHMMSS(historicProcessInstance.getEndTime()));
        historicProcessInstanceWrapper.setStarter(userWrapperBuilder.buildUserWrapper(contextHelper.getCurrentUser()));
        return historicProcessInstanceWrapper;
    }
}
