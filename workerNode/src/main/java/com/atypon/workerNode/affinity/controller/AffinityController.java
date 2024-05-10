package com.atypon.workerNode.affinity.controller;

import com.atypon.workerNode.affinity.service.AffinityService;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/toAffinity")
@RestController
public class AffinityController {
    private final AffinityService affinityService;
    private final String query="/toNode";
    private final String sendBackQuery = "/backToNode";
    @Autowired
    public AffinityController(AffinityService affinityService){
        this.affinityService = affinityService;
    }
    @RequestMapping("/insertPropertyWhere")
    public void insertPropertyWhere(@RequestBody Request request){
        Document document = getDocument(request);
        if(affinityService.insertPropertyToDocument(document,request.getNewKey(),request.getNewValue()))
            affinityService.notifyNodes(request, query + "/insertPropertyWhere");
        else  affinityService.returnMessageToNode(request,sendBackQuery+"/insertPropertyWhere");
    }

    @RequestMapping("/writePropertyWhere")
    public void writePropertyWhere(@RequestBody Request request){
        System.out.println(request.getFromNode());
        Document document = getDocument(request);
        if(affinityService.writePropertyToDocument(document,request.getNewKey(),request.getNewValue(),request.getValue()))
            affinityService.notifyNodes(request, query + "/writePropertyWhere");
        else affinityService.returnMessageToNode(request,sendBackQuery+"/writePropertyWhere");

    }
    @RequestMapping("/deletePropertyWhere")
    public void deletePropertyWhere(@RequestBody Request request){
        Document document = getDocument(request);
        if(affinityService.deletePropertyFromDocument(document,request.getKey()))
            affinityService.notifyNodes(request, query + "/deletePropertyWhere");
        else affinityService.returnMessageToNode(request,sendBackQuery+"/deletePropertyWhere");
    }
    @RequestMapping("/deleteDocumentWhere")
    public void deleteDocumentWhere(@RequestBody Request request){
        Database database = affinityService.getDatabaseByName(request.getDbName(), request.getUsername());
        Document document = affinityService.getDocumentById(database, request.getId());
        if(affinityService.deleteDocument(database,document)) {
            affinityService.notifyNodes(request, query + "/deleteDocumentWhere");
        }
        else affinityService.returnMessageToNode(request,sendBackQuery+"/deleteDocumentWhere");
    }
    private Document getDocument(Request request) {
        Database database = affinityService.getDatabaseByName(request.getDbName(), request.getUsername());
        return affinityService.getDocumentById(database, request.getId());
    }
}
