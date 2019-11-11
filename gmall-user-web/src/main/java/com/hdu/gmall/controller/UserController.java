package com.hdu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdu.gmall.bean.UmsMember;
import com.hdu.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<UmsMember> getUmsMemberList(){
        return userService.getAllUser();
    }
}
