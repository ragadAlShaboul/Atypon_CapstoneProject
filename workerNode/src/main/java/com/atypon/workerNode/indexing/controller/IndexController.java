package com.atypon.workerNode.indexing.controller;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.indexing.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/createConnection")
public class IndexController {
    private final IndexService indexService;
    private final String query="/toNode";
    @Autowired
    public IndexController(IndexService indexService){
        this.indexService=indexService;
    }
    @RequestMapping("/createIndexOn")
    public String createIndexOn(@RequestBody Request request){
        indexService.createIndexOn(request.getUsername(),request.getDbName(),request.getKey());
        indexService.notifyNodes(request,query+"/createIndexOn");
        return "done";
    }
}
