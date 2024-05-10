package com.atypon.workerNode.affinity.controller;

import com.atypon.workerNode.affinity.service.AffinityService;
import com.atypon.workerNode.affinity.utils.AffinityComm;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/backToNode")
public class BackMessagesController {
    private final AffinityService affinityService;

    @Autowired
    public BackMessagesController(AffinityService affinityService){
        this.affinityService=affinityService;
    }

    @RequestMapping("/insertPropertyWhere")
    public void insertPropertyWhere(@RequestBody Request request){
        Document document = getDocument(request);
        AffinityComm.insertPropertyToAffinity(document,request.getKey(),request.getNewValue());
    }
    @RequestMapping("/writePropertyWhere")
    public void writePropertyWhere(@RequestBody Request request){
        System.out.println(request+"------ returned message");
        Document document = getDocument(request);
        AffinityComm.writePropertyToAffinity(document,request.getKey(), request.getNewKey(), document.readProperty(request.getNewKey()),request.getNewValue());
    }
    @RequestMapping("/deletePropertyWhere")
    public void deletePropertyWhere(@RequestBody Request request){
        Document document = getDocument(request);
        AffinityComm.deletePropertyToAffinity(document,request.getKey());
    }
    @RequestMapping("/deleteDocumentWhere")
    public void deleteDocumentWhere(@RequestBody Request request){
        Document document = getDocument(request);
        AffinityComm.deleteDocumentToAffinity(document);
    }
    private Document getDocument(Request request) {
        Database database = affinityService.getDatabaseByName(request.getDbName(), request.getUsername());
        Document document = affinityService.getDocumentById(database, request.getId());
        return document;
    }
}
