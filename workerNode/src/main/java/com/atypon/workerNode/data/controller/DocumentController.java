package com.atypon.workerNode.data.controller;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.data.service.DocumentService;
import com.atypon.workerNode.user.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/createConnection")
public class DocumentController {
    private final DocumentService documentService;
    private final UserService userService;

    @Autowired
    public DocumentController(DocumentService documentService,UserService userService){
        this.documentService=documentService;
        this.userService=userService;
    }
    @RequestMapping("/createDocument")
    public boolean createDocument(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return false;
        Database database = documentService.getDatabaseByName(request.getDbName(),request.getUsername());
        if(database==null)
            return false;
        try {
            int id=documentService.createDocument(request.getDocument(),request.getSchema(),database);
            if(id!=-1){
                String query = "/toNode";
                documentService.notifyNodes(request, query +"/createDocument");
                return true;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    @RequestMapping("/getAllDocuments")
    public String getAllDocuments(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return "bad credentials!!";
        return documentService.getAllDocuments(request.getDbName(),request.getUsername());
    }
    @RequestMapping("/readDocumentsWhere")
    public String readDocumentsWhere(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return "bad credentials!!";
        return documentService.readDocumentsWhere(request.getUsername(), request.getDbName(), request.getKey(), request.getValue());
    }
    @RequestMapping("/deleteDocumentWhere")
    public boolean deleteDocumentWhere(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return false;
        Database database = documentService.getDatabaseByName(request.getDbName(),request.getUsername());
        return documentService.deleteDocumentWhere(database, request.getKey(), request.getValue());
    }
    @RequestMapping("/getDocumentById")
    public JSONObject getDocumentById(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return null;
        Database database = documentService.getDatabaseByName(request.getDbName(),request.getUsername());
        return documentService.getDocumentById(database,request.getId()).getDocument();
    }
    @RequestMapping("/readDocumentWhere")
    public String readDocumentWhere(@RequestBody Request request){
        if(!userService.userExists(request.getUsername(),request.getPassword()))
            return null;
        Database database = documentService.getDatabaseByName(request.getDbName(),request.getUsername());
        ArrayList<Document> documents=documentService.getDocuments(database,request.getKey(),request.getValue());
        return documentService.documentsToString(documents);
    }

}
