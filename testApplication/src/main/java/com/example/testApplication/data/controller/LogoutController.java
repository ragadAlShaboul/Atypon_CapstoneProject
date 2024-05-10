package com.example.testApplication.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogoutController {
    @RequestMapping("/logout")
    public String logoutMapping(){
        return "login";
    }
}
