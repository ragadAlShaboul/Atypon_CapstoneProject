package com.atypon.workerNode.cluster.service;

import com.atypon.workerNode.cluster.model.Node;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.data.repository.DatabaseRepository;
import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.user.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ClusterService {

    public Database getDatabaseByName(String dbName, String username){
        UserRepository userRepository = UserRepository.getUserRepository();
        User user=userRepository.getByUsername(username);
        DatabaseRepository databaseRepository=user.getDatabaseRepository();
        return databaseRepository.getDatabaseByName(dbName);
    }
    public Node getNode(int nodeNum){
        return NodeRepository.getNodeRepository().getNode(nodeNum);
    }
    public int createDocument(JSONObject document, JSONObject documentSchema, Database database, int affinityNum) throws JSONException {
        return database.addDocument(document,documentSchema,getNode(affinityNum));
    }
    public Document getDocumentById(Database database, int id){
        return database.getDocuments().get(id);
    }
    public void insertPropertyToDocument(Document document,String key,Object newValue){
        document.insertProperty(key, newValue);
    }
    public void writePropertyToDocument(Document document,String newKey,Object newValue){
        document.writeProperty(newKey, newValue);
    }
    public void deletePropertyFromDocument(Document document,String key){
        document.deleteProperty(key);
    }
    public void deleteDocument(Database database, Document document){
        database.deleteDocument(document.getId());
    }
    public void createIndexOn(String dbName,String username,String key){
        getDatabaseByName(dbName,username).createIndexOn(key);
    }
}
