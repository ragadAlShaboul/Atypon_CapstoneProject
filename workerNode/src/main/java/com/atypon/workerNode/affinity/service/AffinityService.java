package com.atypon.workerNode.affinity.service;

import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.cluster.communication.ClusterUniCast;
import com.atypon.workerNode.cluster.communication.CommunicationStrategy;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.Request;
import com.atypon.workerNode.data.repository.DatabaseRepository;
import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AffinityService {

    public Database getDatabaseByName(String dbName, String username){
        UserRepository userRepository = UserRepository.getUserRepository();
        User user=userRepository.getByUsername(username);
        DatabaseRepository databaseRepository=user.getDatabaseRepository();
        return databaseRepository.getDatabaseByName(dbName);
    }
    public Document getDocumentById(Database database, int id){
        return database.getDocuments().get(id);
    }
    public boolean insertPropertyToDocument(Document document,String key,Object newValue){
        if(document.readProperty(key)==null) {
            document.insertProperty(key, newValue);
            return true;
        }
        return false;
    }
    public boolean writePropertyToDocument(Document document,String newKey,Object newValue,Object value){
        if(document.readProperty(newKey).equals(value)) {
            document.writeProperty(newKey, newValue);
            return true;
        }
        return false;
    }
    public boolean deletePropertyFromDocument(Document document,String key){
        if(document.readProperty(key)!=null) {
            document.deleteProperty(key);
            return true;
        }
        return false;
    }
    public boolean deleteDocument(Database database, Document document){
        if(document!=null){
            System.out.println("document deleted");
            database.deleteDocument(document.getId());
            return true;
        }
        return false;
    }
    public void returnMessageToNode(Request request, String query){
        System.out.println("affinity problem");
        CommunicationStrategy uniCast = new ClusterUniCast();
        uniCast.send(new Message(request,query),request.getFromNode());
    }
    public void notifyNodes(Request request, String query){
        NodeRepository nodeRepository = NodeRepository.getNodeRepository();
        nodeRepository.notifyUpdates(new Message(request,query));
    }
}
