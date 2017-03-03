package com.jy.activiti.controller;

import com.jy.activiti.common.annotation.RequiredLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredLogin
@Controller
@RequestMapping("/home")
public class HomeController extends BaseController{

    @ResponseBody
    @RequestMapping("/index")
    public Map<String, Object> index() {
        return success();
    }

    /**
     * 请假流程申请页面
     * */
    @RequestMapping("/askforleave")
    public String askforleave() {
        return "pages/ask-for-leave";
    }

    /**
     * 流程管理页面
     * */
    @RequestMapping("/pdm")
    public String pdManagement() {
        return "pages/process-definition-management";
    }

    /**
     * 用户任务列表页面
     * */
    @RequestMapping("/user/task/list")
    public String userTaskList() {
        return "pages/user-task-list";
    }

    /**
     * model页面
     * */
    @RequestMapping("/model")
    public String model() {
        return "pages/model";
    }

    /**
     * identity management
     * */
    @RequestMapping("/identity")
    public String identity() {
        return "pages/identity-management";
    }

}
