package com.atypon.workerNode.cluster.controller;

import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.cluster.service.BootstrapService;
import com.atypon.workerNode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootstrapController {
    UserService userService;

    @Autowired
    public BootstrapController(UserService userService){
        this.userService=userService;
    }
    BootstrapService bootstrapService =new BootstrapService();
    @PostMapping("/userCredentials")
    public String userCredentials(@RequestBody User user){
        System.out.println(user.toString());
        userService.addNewUser(user);
        return "added successfully";
    }
    @PostMapping("/numberOfNodes")
    public String numberOfNodes(@RequestBody String numberOfNodes){
        System.out.println(numberOfNodes);
        bootstrapService.setNodes(Integer.parseInt(numberOfNodes));
        return "received successfully";
    }
    @PostMapping("/nodeInfo")
    public String getCurrentNode(@RequestBody String currentNode){
        System.out.println(currentNode);
        bootstrapService.setCurrentNode(currentNode);
        return "received successfully";
    }
}
