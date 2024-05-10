package com.atypon.workerNode.data.service;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.repository.DatabaseRepository;
import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.user.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    public User getByUsername(String username){
        UserRepository userRepository =UserRepository.getUserRepository();
        return userRepository.getByUsername(username);
    }
    public boolean createDatabase(String dbName, JSONObject schema, String username){
        if(dbName==null||schema==null)
            return false;
        User user =getByUsername(username);
        DatabaseRepository databaseRepository = user.getDatabaseRepository();
        if(databaseRepository.getDatabaseByName(dbName)!=null)
            return false;
        databaseRepository.createDatabase(dbName,schema);
        return true;
    }
    public boolean deleteDatabase(String dbName,String username){
        if(dbName==null)
            return false;
        User user = getByUsername(username);
        DatabaseRepository databaseRepository= user.getDatabaseRepository();
        if(databaseRepository.getDatabaseByName(dbName)==null)
            return false;
        return databaseRepository.deleteDatabase(dbName);
    }
    public Database getDatabaseByName(String dbName, String username){
        User user=getByUsername(username);
        DatabaseRepository databaseRepository=user.getDatabaseRepository();
        if (dbName==null)
            return null;
        return databaseRepository.getDatabaseByName(dbName);
    }
    public void notifyNodes(Request request, String query){
        NodeRepository nodeRepository = NodeRepository.getNodeRepository();
        nodeRepository.notifyUpdates(new Message(request,query));
    }
}
