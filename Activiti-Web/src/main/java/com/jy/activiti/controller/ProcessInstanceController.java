package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.response.entity.HistoricProcessInstanceWrapper;
import com.jy.activiti.response.service.HistoricProcessInstanceWrapperBuilder;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/processinstance")
@RestController
public class ProcessInstanceController extends BaseController {

    @Autowired
    private ContextHelper contextHelper;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoricProcessInstanceWrapperBuilder historicProcessInstanceWrapperBuilder;


    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @RequiredLogin
    public Object userInstanceList() {
        User currentUser = contextHelper.getCurrentUser();
        Map<String, Object> result = new HashMap<>();
        List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery().startedBy(currentUser.getId()).list();
        if (historicProcessInstanceList == null || historicProcessInstanceList.isEmpty()) {
            result.put("processinstances", historicProcessInstanceList);

        } else {
            List<HistoricProcessInstanceWrapper> historicProcessInstanceWrapperList = new ArrayList<>(historicProcessInstanceList.size());
            historicProcessInstanceList.forEach(historicProcessInstance -> historicProcessInstanceWrapperList.add(historicProcessInstanceWrapperBuilder.buildHistoricProcessInstanceWrapper(historicProcessInstance)));
            result.put("processinstances", historicProcessInstanceWrapperList);
        }
        return success(result);
    }
}
