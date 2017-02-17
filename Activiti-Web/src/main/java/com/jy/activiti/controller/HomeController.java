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
        return "pages/askforleave";
    }

    /**
     * 流程管理页面
     * */
    @RequestMapping("/pdm")
    public String pdManagement() {
        return "pages/processdefinitionmanagement";
    }

}
