package com.jy.activiti.controller;

import com.jy.activiti.controller.validate.group.GroupB;
import com.jy.activiti.controller.validate.group.GroupC;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RequestMapping("/validate")
@RestController
public class ValidateController extends BaseController{

    @RequestMapping(method = RequestMethod.GET)
    public Object getValidate(Model model, HttpServletRequest request, @Validated( { GroupB.class,GroupC.class }) ValidateObject validateModel, BindingResult result) {
        if(result.hasErrors()){
            return fail(result.getFieldError().getDefaultMessage());
        }
        System.out.println();
        return success();
    }
}

class ValidateObject{

    @NotNull(message = "error", groups = { GroupB.class })
    private Long id;

    public Long getId() {
        return id;
    }

    public ValidateObject setId(Long id) {
        this.id = id;
        return this;
    }
}
