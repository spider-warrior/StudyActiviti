package com.jy.activiti.controller;

import com.jy.activiti.common.enums.ResponseCode;
import com.jy.activiti.common.util.StringUtil;
import com.jy.activiti.helper.LoginHelper;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginHelper loginHelper;

    @RequestMapping("/login")
    public Object login(@RequestBody Map<String, String> param, HttpServletResponse response) {
        String username = param.get("username");
        String password = param.get("password");
        String code = param.get("code");

        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return fail(ResponseCode.REQUEST_PARAM_ERROR.getValue());
        }
        User user = loginHelper.login(username, password, code);
        //验证失败
        if (user == null) {
            return fail(ResponseCode.REQUEST_PARAM_ERROR.getValue());
        }
        loginHelper.createSession(user, response);
        return success();
    }

    @RequestMapping("/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        loginHelper.logout(request, response);
        return success();
    }

}
