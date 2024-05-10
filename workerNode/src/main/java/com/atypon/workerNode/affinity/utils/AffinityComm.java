package com.atypon.workerNode.affinity.utils;

import com.atypon.workerNode.cluster.communication.ClusterUniCast;
import com.atypon.workerNode.cluster.communication.CommunicationStrategy;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.Request;
import com.atypon.workerNode.cluster.repository.NodeRepository;

public class AffinityComm {
    private static final String query = "/toAffinity";
    private static final String nodeURL = NodeRepository.getCurrentNode();
    private static final CommunicationStrategy uniCast = new ClusterUniCast();

    public static void writePropertyToAffinity(Document document,String key,String newKey,Object value,Object newValue){
        Database database = document.getDatabase();
        String username = database.getUser().getUsername();
        Request request = new Request.Builder(database.getDbName(),username,nodeURL)
                .key(key)
                .value(value)
                .newValue(newValue)
                .newKey(newKey)
                .id(document.getId()).build();
        uniCast.send(new Message(request,query+"/writePropertyWhere"), document.getAffinity());
    }
    public static void insertPropertyToAffinity(Document document,String key,Object newValue){
        Database database = document.getDatabase();
        String username = database.getUser().getUsername();
        Request request = new Request.Builder(database.getDbName(),username,nodeURL)
                .key(key)
                .newValue(newValue)
                .newKey(key)
                .id(document.getId()).build();
        uniCast.send(new Message(request,query+"/insertPropertyWhere"),document.getAffinity());
    }
    public static void deletePropertyToAffinity(Document document,String key){
        Database database = document.getDatabase();
        String username = database.getUser().getUsername();
        Request request = new Request.Builder(database.getDbName(),username,nodeURL)
                .key(key)
                .id(document.getId()).build();
        uniCast.send(new Message(request,query+"/deletePropertyWhere"),document.getAffinity());
    }
    public static boolean deleteDocumentToAffinity(Document document){
        Database database = document.getDatabase();
        String username = database.getUser().getUsername();
        Request request = new Request.Builder(database.getDbName(),username,nodeURL)
                .id(document.getId()).build();
        uniCast.send(new Message(request,query+"/deleteDocumentWhere"),document.getAffinity());
        return true;
    }
}
