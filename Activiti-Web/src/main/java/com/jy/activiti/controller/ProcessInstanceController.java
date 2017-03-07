package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.helper.ContextHelper;
import com.jy.activiti.response.entity.HistoricProcessInstanceWrapper;
import com.jy.activiti.response.service.HistoricProcessInstanceWrapperBuilder;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
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
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
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

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    @RequiredLogin
    public Object userInstanceList(@PathVariable("id") String processInstanceId) throws IOException {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance == null) {
            return failSourceNotFound(ResourcesType.HISTORICPROCESSINSTANCE.getValue());
        }
        //流程定义
        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        //正在活动节点
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(historicProcessInstance.getId());
        ProcessDiagramGenerator pdg = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        //生成流图片
        InputStream inputStream = pdg.generateDiagram(bpmnModel, "PNG", activeActivityIds, activeActivityIds,
        processEngine.getProcessEngineConfiguration().getActivityFontName(),
        processEngine.getProcessEngineConfiguration().getLabelFontName(),
        processEngine.getProcessEngineConfiguration().getActivityFontName(),
        processEngine.getProcessEngineConfiguration().getProcessEngineConfiguration().getClassLoader(), 1.0);
        byte[] content = new byte[inputStream.available()];
        inputStream.read(content);
        return content;
    }

}
