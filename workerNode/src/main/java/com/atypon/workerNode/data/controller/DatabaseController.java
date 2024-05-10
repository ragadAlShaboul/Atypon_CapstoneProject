package com.atypon.workerNode.data.controller;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.data.service.DatabaseService;
import com.atypon.workerNode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/createConnection")
public class DatabaseController {
    private final DatabaseService databaseService;
    private final UserService userService;
    private final String query="/toNode";
    @Autowired
    public DatabaseController(DatabaseService databaseService, UserService userService){
        this.databaseService=databaseService;
        this.userService = userService;
    }
    @RequestMapping("/createDatabase")
    public boolean createDatabase(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return false;
        if(!databaseService.createDatabase(request.getDbName(),request.getSchema(),request.getUsername()))
            return false;
        databaseService.notifyNodes(request,query+"/createDatabase");
        return true;
    }
    @RequestMapping("/deleteDatabase")
    public boolean deleteDatabase(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return false;
        if(databaseService.deleteDatabase(request.getDbName(),request.getUsername())) {
            databaseService.notifyNodes(request,query+"/deleteDatabase");
            return true;
        }
        return false;
    }
}
