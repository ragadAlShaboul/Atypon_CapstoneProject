package com.atypon.workerNode.cluster.controller;

import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.Request;
import com.atypon.workerNode.cluster.service.ClusterService;
import com.atypon.workerNode.data.service.DatabaseService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/toNode")
@RestController
public class ClusterController {
    private final DatabaseService databaseService;
    private final ClusterService clusterService;

    @Autowired
    public ClusterController(DatabaseService databaseService, ClusterService clusterService){
        this.databaseService=databaseService;
        this.clusterService=clusterService;
    }

    @RequestMapping("/createDatabase")
    public void createDatabase(@RequestBody Request request){
        System.out.println(request.toString()+"-");
        databaseService.createDatabase(request.getDbName(),request.getSchema(),request.getUsername());
    }
    @RequestMapping("/deleteDatabase")
    public boolean deleteDatabase(@RequestBody Request request){
        System.out.println(request.toString()+"-");
        return databaseService.deleteDatabase(request.getDbName(),request.getUsername());
    }
    @RequestMapping("/createDocument")
    public void createDocument(@RequestBody Request request){
        System.out.println(request.toString()+"-");
        Database database = clusterService.getDatabaseByName(request.getDbName(), request.getUsername());
        try {
            clusterService.createDocument(request.getDocument(),request.getSchema(),database,request.getAffinityNum());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @RequestMapping("/writePropertyWhere")
    public boolean writePropertyWhere(@RequestBody Request request){
        System.out.println(request.toString());
        Database database = clusterService.getDatabaseByName(request.getDbName(),request.getUsername());
        Document document = clusterService.getDocumentById(database,request.getId());
        clusterService.writePropertyToDocument(document,request.getNewKey(),request.getNewValue());
        return true;
    }
    @RequestMapping("/insertPropertyWhere")
    public boolean insertPropertyWhere(@RequestBody Request request){
        System.out.println(request.toString()+"-");
        Database database = clusterService.getDatabaseByName(request.getDbName(),request.getUsername());
        Document document = clusterService.getDocumentById(database,request.getId());
        clusterService.insertPropertyToDocument(document,request.getKey(),request.getNewValue());
        return true;
    }
    @RequestMapping("/deletePropertyWhere")
    public boolean deletePropertyWhere(@RequestBody Request request){
        System.out.println(request.toString()+"-");
        Database database = clusterService.getDatabaseByName(request.getDbName(),request.getUsername());
        Document document = clusterService.getDocumentById(database,request.getId());
        clusterService.deletePropertyFromDocument(document,request.getKey());
        return true;
    }
    @RequestMapping("/deleteDocumentWhere")
    public boolean deleteDocumentWhere(@RequestBody Request request){
        System.out.println(request.toString()+"-");
        Database database = clusterService.getDatabaseByName(request.getDbName(),request.getUsername());
        Document document = clusterService.getDocumentById(database,request.getId());
        clusterService.deleteDocument(database,document);
        return true;
    }
    @RequestMapping("/createIndexOn")
    public void createIndexOn(@RequestBody Request request){
        clusterService.createIndexOn(request.getDbName(), request.getUsername(), request.getKey());
    }
}
