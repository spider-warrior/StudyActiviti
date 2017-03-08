package com.jy.activiti.response.service;

import com.jy.activiti.response.entity.VariableInstanceWrapper;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class VariableInstanceWrapperBuilder {

    private static final VariableInstanceWrapper empty = new VariableInstanceWrapper();

    public VariableInstanceWrapper buildVariableInstanceWrapper(VariableInstance variableInstance) {
        if (variableInstance == null) {
            return empty;
        }
        VariableInstanceWrapper variableInstanceWrapper = new VariableInstanceWrapper();
        variableInstanceWrapper.setId(variableInstance.getId());
        variableInstanceWrapper.setName(variableInstance.getName());
        variableInstanceWrapper.setTypeName(variableInstance.getTypeName());
        variableInstanceWrapper.setValue(variableInstance.getValue());
        variableInstanceWrapper.setProcessInstanceId(variableInstance.getProcessInstanceId());
        variableInstanceWrapper.setExecutionId(variableInstance.getExecutionId());
        variableInstanceWrapper.setTaskId(variableInstance.getTaskId());
        return variableInstanceWrapper;
    }
    public List<VariableInstanceWrapper> buildVariableInstanceWrapper(Map<String, Object> variables) {
        if (variables == null || variables.isEmpty()) {
            return Collections.emptyList();
        }
        List<VariableInstanceWrapper> variableInstanceWrapperList = new ArrayList<>(variables.size());
        for (Map.Entry<String, Object> entry: variables.entrySet()) {
            VariableInstanceWrapper variableInstanceWrapper = new VariableInstanceWrapper();
            variableInstanceWrapper.setName(entry.getKey());
            variableInstanceWrapper.setValue(entry.getValue());
            variableInstanceWrapperList.add(variableInstanceWrapper);
        }
        return variableInstanceWrapperList;
    }

}
