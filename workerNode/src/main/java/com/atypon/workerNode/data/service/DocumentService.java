package com.atypon.workerNode.data.service;

import com.atypon.workerNode.Request;
import com.atypon.workerNode.affinity.service.AffinityService;
import com.atypon.workerNode.affinity.utils.AffinityBalanceStrategy;
import com.atypon.workerNode.affinity.utils.AffinityBalancer;
import com.atypon.workerNode.affinity.utils.AffinityComm;
import com.atypon.workerNode.cluster.model.Message;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.indexing.model.IndexTable;
import com.atypon.workerNode.indexing.respository.IndexRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.TreeMap;

@Service
public class DocumentService {
    private final AffinityService affinityService= new AffinityService();
    private final String currentNode = NodeRepository.getCurrentNode();
    private final String query = "/toNode";
    public int createDocument(JSONObject document, JSONObject documentSchema, Database database) throws JSONException {
        if (document==null||documentSchema==null||database==null)
            return -1;
        AffinityBalanceStrategy affinityBalancer = new AffinityBalancer();
        return database.addDocument(document,documentSchema,affinityBalancer.getAffinityNode());
    }
    public String getAllDocuments(String dbName,String username){
        Database database=getDatabaseByName(dbName,username);
        if(database==null||dbName==null)
            return null;
        TreeMap<Object, Document> documents = database.getDocuments();
        StringBuilder strDocument = new StringBuilder();
        documents.forEach((index,document)->{
            strDocument.append(document.getDocument().toString()).append("\n");
        });
        return strDocument.toString();
    }
    public String readDocumentsWhere(String username,String dbName,String key,Object value){
        Database database = getDatabaseByName(dbName,username);
        if(database==null||key==null||value==null)
            return null;
        ArrayList<Document> documents = getDocuments(database,key,value);
        StringBuilder strDocument = new StringBuilder();
        for (Document document:documents)
            strDocument.append(document.getDocument().toString()).append("\n");
        return strDocument.toString();
    }
    public Database getDatabaseByName(String dbName, String username){
        DatabaseService databaseService = new DatabaseService();
        return databaseService.getDatabaseByName(dbName,username);
    }
    public ArrayList<Document> getDocuments(Database database,String key,Object value){
        if(database==null||key==null||value==null)
            return null;
        IndexRepository indexRepository = new IndexRepository(database);
        if(indexRepository.hasIndexOn(key)){
            IndexTable indexTable = database.getIndexOn(key);
            return (ArrayList<Document>) indexTable.getIndexTree().get(value);
        } else{
            return getDocumentsList(database, key, value);
        }

    }
    private static ArrayList<Document> getDocumentsList(Database database, String key, Object value) {
        ArrayList<Document> documents = new ArrayList<>();
        TreeMap<Object,Document> documentTreeMap = database.getDocuments();
        documentTreeMap.forEach((id,document)->{
            try {
                if(document.getDocument().has(key))
                    if(document.getDocument().get(key).equals(value))
                        documents.add(document);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        });
        return documents;
    }

    public Document getDocumentById(Database database,int id){
        return database.getDocuments().get(id);
    }
    public String documentsToString(ArrayList<Document> documents){
        StringBuilder stringBuilder = new StringBuilder();
        if(documents!=null)
            for (Document document:documents){
                stringBuilder.append(document.getDocument().toString()).append("\n");
            }
        return stringBuilder.toString();
    }
    public void notifyNodes(Request request, String query){
        NodeRepository nodeRepository = NodeRepository.getNodeRepository();
        nodeRepository.notifyUpdates(new Message(request,query));
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
    public boolean nodeHasAffinity(Document document){
        return document.hasAffinity();
    }
}
