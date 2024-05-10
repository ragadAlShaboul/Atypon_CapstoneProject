package com.example.testApplication.data.controller;


import com.example.testApplication.data.model.*;
import com.example.testApplication.data.srevice.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.testApplication.data.srevice.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    LoginService loginService = new LoginService();
    UserService userService = new UserService();
    @RequestMapping("/")
    public String root(Model model){
        return "login";
    }
    @RequestMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model){
        String page = loginService.getUserRole(name,password);
        if(page.equals("login"))
            model.addAttribute("message", "bad credentials!!");
        if(page.equals("user"))
            model.addAttribute("userModel",new UserModel(name,"","",userService.getUser(name).getBalance()));
        if(page.equals("admin"))
            model.addAttribute("adminModel",new AdminModel(new ArrayList<>(),new ArrayList<>(),name,""));
        if(page.equals("employee"))
            model.addAttribute("employeeModel",new EmployeeModel(0,name,"",""));
        return page;
    }
}
