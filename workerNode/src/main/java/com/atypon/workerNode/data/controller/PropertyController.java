package com.atypon.workerNode.data.controller;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.service.PropertyService;
import com.atypon.workerNode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/createConnection")
public class PropertyController {
    private final PropertyService propertyService;
    private final UserService userService;
    @Autowired
    public PropertyController(PropertyService propertyService,UserService userService){
        this.propertyService=propertyService;
        this.userService=userService;
    }

    @RequestMapping("/readPropertyWhere")
    public String readPropertyWhere(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return "bad credentials!!";
        Database database = propertyService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.readPropertyWhere(database,request.getKey(),request.getValue(),request.getKeyToRead());
    }
    @RequestMapping("/deletePropertyWhere")
    public boolean deletePropertyWhere(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return false;
        Database database = propertyService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.deletePropertyWhere(database, request.getKey(), request.getValue());
    }
    @RequestMapping("/writePropertyWhere")
    public boolean writePropertyWhere(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return false;
        Database database = propertyService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.writePropertyWhere(database, request.getKey(), request.getNewKey(),request.getValue(), request.getNewValue());
    }
    @RequestMapping("/insertPropertyWhere")
    public boolean insertPropertyWhere(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return false;
        Database database = propertyService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.insertPropertyWhere(database, request.getKey(), request.getValue(), request.getNewKey(), request.getNewValue());
    }
}
