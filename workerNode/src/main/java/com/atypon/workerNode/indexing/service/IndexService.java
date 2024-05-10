package com.atypon.workerNode.indexing.service;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.service.DatabaseService;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
    private Database getDatabaseByName(String dbName,String username){
        DatabaseService databaseService = new DatabaseService();
        return databaseService.getDatabaseByName(dbName,username);
    }
    public void createIndexOn(String username,String dbName,String key){
        Database database = getDatabaseByName(dbName,username);
        database.createIndexOn(key);
    }
    public void notifyNodes(Request request, String query){
        NodeRepository nodeRepository = NodeRepository.getNodeRepository();
        nodeRepository.notifyUpdates(new Message(request,query));
    }
}
