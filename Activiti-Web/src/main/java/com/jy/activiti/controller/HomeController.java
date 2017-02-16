package com.jy.activiti.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/home")
public class HomeController extends BaseController{

    @RequestMapping("/index")
    public Map<String, Object> index() {
        return success();
    }
}
