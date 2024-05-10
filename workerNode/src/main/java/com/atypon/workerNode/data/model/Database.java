package com.atypon.workerNode.data.model;

import com.atypon.workerNode.cluster.model.Node;
import com.atypon.workerNode.data.repository.DocumentRepository;
import com.atypon.workerNode.indexing.model.IndexTable;
import com.atypon.workerNode.indexing.respository.IndexRepository;
import com.atypon.workerNode.user.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

public class Database {
    private JSONObject schema;
    private final int id;
    private final String dbName;
    private final User user;
    private final DocumentRepository documentRepository;
    private final IndexRepository indexRepository;
    public Database(JSONObject schema,int id,String dbName,User user) {
        this.user=user;
        this.dbName=dbName;
        this.schema = schema;
        this.id = id;
        documentRepository = new DocumentRepository(this);
        indexRepository=new IndexRepository(this);
   }
   public IndexRepository getIndexRepository(){
        return indexRepository;
   }
    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }
    public User getUser() {
        return user;
    }
    public int addDocument(JSONObject document, JSONObject documentSchema, Node affinityNode){
        try {
            return documentRepository.createDocument(document,documentSchema,affinityNode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteDocument(int documentId){
        try {
            return documentRepository.deleteDocument(documentId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public String getDbName() {
        return dbName;
    }
    public JSONObject getSchema() {
        return schema;
    }
    public void setSchema(JSONObject schema) {
        this.schema = schema;
    }
    public int getId() {
        return id;
    }
    public IndexTable getIndexOn(String key){
        return indexRepository.getIndexOn(key);
    }
    public IndexTable createIndexOn(String key){
        if(indexRepository.hasIndexOn(key))
            return getIndexOn(key);
        return indexRepository.addIndexTable(key);
    }
    public TreeMap<Object, Document> getDocuments() {
        return documentRepository.getDocuments();
    }
    @Override
    public String toString() {
        return "Database: " +
                "schema=" + schema +
                ", {id=" + id +
                ", dbName='" + dbName + '\'' +
                ", documents=" + documentRepository.getDocuments() +
                '}';
    }
}
