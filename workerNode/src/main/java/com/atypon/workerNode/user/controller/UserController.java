package com.atypon.workerNode.user.controller;

import com.atypon.workerNode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @RequestMapping("/createConnection")
    public String createConnection(@RequestHeader String username, @RequestHeader String password){
        if(!userService.userExists(username,password))
            return "user doesn't exists!";
        else return "successfully connected.";
    }
}
