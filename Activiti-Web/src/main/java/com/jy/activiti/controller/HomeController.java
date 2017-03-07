package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredLogin
@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @ResponseBody
    @RequestMapping("/index")
    public Map<String, Object> index() {
        return success();
    }

    /**
     * 请假流程申请页面
     */
    @RequestMapping("/askforleave")
    public String askforleave() {
        return "pages/ask-for-leave";
    }

    /**
     * 流程管理页面
     */
    @RequestMapping("/processdefinition")
    public String processDefinition() {
        return "pages/process-definition-list";
    }

    /**
     * 流程管理页面
     */
    @RequestMapping("/processdefinition/{id}")
    public String processDefinitionDetail(@PathVariable("id") String id) {
        return "pages/process-definition-detail";
    }

    /**
     * 流程跳转申请
     * */
    @RequestMapping("/processdefinition/start/{id}")
    public String processDefinitionStart(@PathVariable("id") String id) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
        if (processDefinition == null) {
            return "404";
        }
        return "pages/" + processDefinition.getKey();
    }

    /**
     * 用户流程页面
     */
    @RequestMapping("/user/processinstance")
    public String userProcessInstance() {
        return "pages/user-process-instance-list";
    }

    /**
     * 用户流程实例详情页面
     */
    @RequestMapping("/user/processinstance/{id}")
    public String userProcessInstanceDetail() {
        return "pages/process-instance-detail";
    }

    /**
     * 用户任务列表页面
     */
    @RequestMapping("/user/task/list")
    public String userTaskList() {
        return "pages/user-task-list";
    }

    /**
     * model页面
     */
    @RequestMapping("/model")
    public String model() {
        return "pages/process-model-list";
    }

    /**
     * identity management
     */
    @RequestMapping("/identity")
    public String identity() {
        return "pages/identity-management";
    }

}
