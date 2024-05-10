package com.atypon.bootstrappingNode.user.controller;

import com.atypon.bootstrappingNode.user.model.User;
import com.atypon.bootstrappingNode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/assignNewUser")
    public String assignNewUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(userService.userExists(username,password))
            return userService.getUserURL(username);
        if(userService.userExists(username))
            return "user already exists, bad credentials!";
        User user = new User(username,password);
        userService.assignNewUser(user);
        userService.sendUserInformation(user);
        return userService.getUserURL(username);
    }
}
