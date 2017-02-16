package com.jy.activiti.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController extends BaseController{

    @RequestMapping("/index")
    public Map<String, Object> index() {
        return success();
    }
}
