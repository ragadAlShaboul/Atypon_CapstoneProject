package com.atypon.workerNode.data.service;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.affinity.service.AffinityService;
import com.atypon.workerNode.affinity.utils.AffinityComm;
import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.data.repository.DatabaseRepository;
import com.atypon.workerNode.indexing.model.IndexTable;
import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PropertyService {

    private final AffinityService affinityService= new AffinityService();
    private final String currentNode = NodeRepository.getCurrentNode();
    private final String query = "/toNode";

    public User getByUsername(String username){
        UserRepository userRepository =UserRepository.getUserRepository();
        return userRepository.getByUsername(username);
    }

    public Database getDatabaseByName(String dbName, String username){
        User user=getByUsername(username);
        DatabaseRepository databaseRepository=user.getDatabaseRepository();
        if (dbName==null)
            return null;
        return databaseRepository.getDatabaseByName(dbName);
    }
    public String readPropertyWhere(Database database, String key, Object value, String keyToRead){
        if(database==null||key==null||value==null||keyToRead==null)
            return "";
        ArrayList<Document> documents=getDocuments(database,key,value);
        StringBuilder stringBuilder= new StringBuilder();
        if(documents!=null)
            for (Document document:documents){
                stringBuilder.append(document.readProperty(keyToRead).toString()).append("\n");
            }
        return stringBuilder.toString();
    }
    public boolean deletePropertyWhere(Database database,String key,Object value) {
        if(database==null||key==null||value==null)
            return false;
        ArrayList<Document> documents=getDocuments(database,key,value);
        if(documents==null)
            return false;
        for (Document document : documents) {
            if(nodeHasAffinity(document)) {
                if (affinityService.deletePropertyFromDocument(document, key))
                    notifyNodes(new Request.Builder(database.getDbName(), database.getUser().getUsername(), currentNode)
                            .key(key)
                            .value(value)
                            .newKey(key)
                            .newValue(value)
                            .id(document.getId()).build(), query + "/deletePropertyWhere");
            }else AffinityComm.deletePropertyToAffinity(document,key);
        }
        return true;
    }
    public boolean writePropertyWhere(Database database,String key,String newKey,Object value,Object newValue){
        if(database==null||key==null||value==null||newValue==null||newKey==null)
            return false;
        ArrayList<Document> documents=getDocuments(database,key,value);
        if(documents==null)
            return false;
        for (Document document:documents){
            if(nodeHasAffinity(document)) {
                if (affinityService.writePropertyToDocument(document, newKey, newValue, document.readProperty(newKey)))
                    notifyNodes(new Request.Builder(database.getDbName(), database.getUser().getUsername(), currentNode)
                            .key(key)
                            .value(value)
                            .id(document.getId()).build(), query + "/writePropertyWhere");
            } else AffinityComm.writePropertyToAffinity(document,key,newKey,document.readProperty(newKey),newValue);
        }
        return true;
    }
    public boolean insertPropertyWhere(Database database,String key,Object value,String newKey,Object newValue) {
        if ((database == null) || (key == null) || (value == null) || (newKey == null) || (newValue == null))
            return false;
        ArrayList<Document> documents = getDocuments(database, key, value);
        if (documents == null)
            return false;
        for (Document document : documents) {
            if (nodeHasAffinity(document)) {
                if (affinityService.insertPropertyToDocument(document, newKey, newValue))
                    notifyNodes(new Request.Builder(database.getDbName(), database.getUser().getUsername(), currentNode)
                            .key(key)
                            .value(value)
                            .newValue(newValue)
                            .newKey(newKey)
                            .id(document.getId()).build(), query + "/insertPropertyWhere");
            }  else AffinityComm.insertPropertyToAffinity(document, newKey, newValue);
        }
        return true;
    }
    public boolean deleteDocumentWhere(Database database,String key,Object value){
        if (database==null||key==null||value==null)
            return false;
        ArrayList<Document> documents=getDocuments(database,key,value);
        if (documents==null)
            return false;
        for (Document document:documents){
            if(nodeHasAffinity(document)) {
                if(affinityService.deleteDocument(database,document))
                    notifyNodes(new Request.Builder(database.getDbName(),database.getUser().getUsername(),currentNode)
                            .key(key)
                            .value(value)
                            .id(document.getId()).build(), query + "/deleteDocumentWhere");
            }else AffinityComm.deleteDocumentToAffinity(document);
        }
        return true;
    }
    public ArrayList<Document> getDocuments(Database database,String key,Object value){
        DocumentService documentService = new DocumentService();
        return documentService.getDocuments(database,key,value);
    }
    public Document getDocumentById(Database database,int id){
        return database.getDocuments().get(id);
    }
   
    public void notifyNodes(Request request, String query){
        NodeRepository nodeRepository = NodeRepository.getNodeRepository();
        nodeRepository.notifyUpdates(new Message(request,query));
    }
    public boolean nodeHasAffinity(Document document){
        return document.hasAffinity();
    }
}
