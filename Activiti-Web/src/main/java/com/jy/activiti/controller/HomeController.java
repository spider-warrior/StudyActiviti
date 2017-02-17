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

    @RequestMapping(value = {"/askforleave"})
    public String askforleave() {
        return "pages/askforleave";
    }

}
