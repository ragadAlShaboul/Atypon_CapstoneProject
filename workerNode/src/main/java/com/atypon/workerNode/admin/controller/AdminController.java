package com.atypon.workerNode.admin.controller;

import com.atypon.workerNode.admin.service.AdminService;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.Request;
import com.atypon.workerNode.data.service.DatabaseService;
import com.atypon.workerNode.data.service.DocumentService;
import com.atypon.workerNode.data.service.PropertyService;
import com.atypon.workerNode.indexing.service.IndexService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final DatabaseService databaseService;
    private final DocumentService documentService;
    private final PropertyService propertyService;
    private final IndexService indexService;
    private final String query="/toNode";

    @Autowired
    public AdminController(AdminService adminService, DatabaseService databaseService,
                           DocumentService documentService, PropertyService propertyService,
                           IndexService indexService){
        this.adminService=adminService;
        this.databaseService=databaseService;
        this.documentService=documentService;
        this.indexService=indexService;
        this.propertyService=propertyService;
    }

    @RequestMapping("/getAllUsers")
    public String getAllUsers(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        return adminService.getAllUsers();
    }
    @RequestMapping("/getAllDatabases")
    public String getAllDatabases(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        return adminService.getAlDatabases(request.getUsername());
    }
    @RequestMapping("/createDatabase")
    public String createDatabase(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        if(!databaseService.createDatabase(request.getDbName(),request.getSchema(),request.getUsername()))
            return "Something bad Happened please check the parameters and try again!";
        databaseService.notifyNodes(request,query+"/createDatabase");
        return "Database "+request.getDbName()+" is added successfully";
    }
    @RequestMapping("/deleteDatabase")
    public String deleteDatabase(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        if(databaseService.deleteDatabase(request.getDbName(),request.getUsername())) {
            databaseService.notifyNodes(request,query+"/deleteDatabase");
            return "Database successfully deleted";
        }
        return "couldn't delete database";
    }
    @RequestMapping("/createDocument")
    public String createDocument(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        if(database==null)
            return "please enter a valid database name!";
        try {
            int id=documentService.createDocument(request.getDocument(),request.getSchema(),database);
            if(id!=-1){
                documentService.notifyNodes(request,query+"/createDocument");
                return "document added successfully, id: "+id;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return "a problem has occurred";
    }
    @RequestMapping("/getAllDocuments")
    public String getAllDocuments(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        return documentService.getAllDocuments(request.getDbName(),request.getUsername());
    }
    @RequestMapping("/readPropertyWhere")
    public String readPropertyWhere(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.readPropertyWhere(database,request.getKey(),request.getValue(),request.getKeyToRead());
    }
    @RequestMapping("/deletePropertyWhere")
    public boolean deletePropertyWhere(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return false;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.deletePropertyWhere(database, request.getKey(), request.getValue());
    }
    @RequestMapping("/writePropertyWhere")
    public String writePropertyWhere(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        if(!propertyService.writePropertyWhere(database,request.getKey(),request.getNewKey(),request.getValue(),request.getNewValue()))
            return "something bad happened please check your arguments and try again";
        return "done";
    }
    @RequestMapping("/insertPropertyWhere")
    public String insertPropertyWhere(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        if(!propertyService.insertPropertyWhere(database,request.getKey(),request.getValue(),request.getNewKey(),request.getNewValue()))
            return "something bad happened please check your arguments and try again";
        return "done";
    }
    @RequestMapping("/deleteDocumentWhere")
    public boolean deleteDocumentWhere(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return false;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.deleteDocumentWhere(database, request.getKey(), request.getValue());
    }
    @RequestMapping("/getDocumentById")
    public JSONObject getDocumentById(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        return propertyService.getDocumentById(database,request.getId()).getDocument();
    }
    @RequestMapping("/readDocumentWhere")
    public String readDocumentWhere(@RequestBody Request request){
        if(!adminService.adminExists(request.getAdminName(),request.getPassword()))
            return null;
        Database database = databaseService.getDatabaseByName(request.getDbName(),request.getUsername());
        ArrayList<Document> documents=documentService.getDocuments(database,request.getKey(),request.getValue());
        return documentService.documentsToString(documents);
    }
    @RequestMapping("/createIndexOn")
    public String createIndexOn(@RequestBody Request request){
        indexService.createIndexOn(request.getUsername(),request.getDbName(),request.getKey());
        databaseService.notifyNodes(request,query+"/createIndexOn");
        return "done";
    }

}
