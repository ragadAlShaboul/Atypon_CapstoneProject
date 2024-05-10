package com.atypon.workerNode.data.repository;

import com.atypon.workerNode.cluster.model.Node;
import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.fileSystem.FileSystem;
import com.atypon.workerNode.fileSystem.FileSystemOperations;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

public class DocumentRepository {
    private final TreeMap<Object, Document> documents=new TreeMap<>(); // default indexing by id
    private final Database database ;
    private final String docPath;
    private int id = 0;
    private final FileSystem fileSystem = new FileSystemOperations();
    public DocumentRepository(Database database){
        this.database=database;
        docPath ="LFS/" + database.getUser().getUsername()+"/"+database.getDbName()+"/";
    }
    public synchronized int createDocument(JSONObject doc, JSONObject schema, Node affinityNode) throws JSONException {
        doc.put("id",id);
        schema.put("id","int");
        Document document = new Document(id,doc,schema,database,affinityNode);
        document.setDocument(doc);
        addDocumentSchema(schema,id);
        if(!document.validate()){
            return -1;
        }
        addDocument(document);
        addDocumentToFile(document);
        updateIDIndexFile();
        return id++;
    }
    public synchronized void updateDocument(Document document) throws JSONException {
        if(!document.validate()){
            return;
        }
        addDocumentSchema(document.getSchema(),document.getId());
        addDocument(document);
        addDocumentToFile(document);
        updateIndexTables();
        updateIDIndexFile();
    }
    private void updateIndexTables(){
        database.getIndexRepository().updateIndexTables();
    }
    private void addDocument(Document document){
        documents.put(document.getId(),document);
    }
    private void addDocumentToFile(Document document){
        String path = docPath +"document"+document.getId()+".json";
        fileSystem.writeToFile(document.getDocument(),path,false);
    }
    public synchronized boolean deleteDocument(int id) throws JSONException {
        removeDocumentSchema(id);
        documents.remove(id);
        updateIDIndexFile();
        String path = docPath + "document" + id + ".json";
        updateIndexTables();
        return fileSystem.deleteFile(path);
    }
    private void addDocumentSchema(JSONObject schema,int documentId) throws JSONException {
        JSONObject dbSchema = database.getSchema();
        JSONObject documentsSchema=dbSchema.getJSONObject("documentSchemas");
        documentsSchema.put(String.valueOf(documentId),schema);
        updateSchemaFile(dbSchema);
        database.setSchema(dbSchema);
    }
    private void removeDocumentSchema(int documentId) throws JSONException {
        JSONObject dbSchema = database.getSchema();
        JSONObject documentsSchema=dbSchema.getJSONObject("documentSchemas");
        documentsSchema.remove(String.valueOf(documentId));
        updateSchemaFile(dbSchema);
        database.setSchema(dbSchema);
    }
    private void updateSchemaFile(JSONObject dbSchema){
        DatabaseRepository databaseRepository=database.getUser().getDatabaseRepository();
        databaseRepository.createSchemaFile(database.getDbName(),dbSchema);
    }
    private void updateIDIndexFile(){
        String path = "LFS/"+database.getUser().getUsername()+
                "/"+database.getDbName()+ "/id_Index.txt";
        if(fileSystem.deleteFile(path))
            fileSystem.writeToFile(documents,path,true);
    }
    public TreeMap<Object,Document> getDocuments() {
        return documents;
    }
}
