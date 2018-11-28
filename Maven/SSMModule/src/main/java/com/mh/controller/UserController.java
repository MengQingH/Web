package com.mh.controller;

import com.mh.pojo.User;
import com.mh.service.UserService;
import com.mh.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping("show")
    String show(HttpServletRequest request) {
        List<User> user = userServiceImpl.show();
        request.setAttribute("user",user);
        System.out.println("执行控制器");
        return "/index.jsp";
    }
}

