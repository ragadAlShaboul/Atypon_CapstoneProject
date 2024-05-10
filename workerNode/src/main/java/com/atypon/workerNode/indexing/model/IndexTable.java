package com.atypon.workerNode.indexing.model;

import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.fileSystem.FileSystem;
import com.atypon.workerNode.fileSystem.FileSystemOperations;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class IndexTable<Key>{
    private TreeMap<Key, ArrayList<Document>> indexTree = new TreeMap<>();
    private final Database database;
    private TreeMap<Object,Document> documents;
    private final String key;
    public IndexTable(String key, Database database){
        this.key=key;
        this.database=database;
        this.documents=database.getDocuments();
        this.indexTree=createIndexTable();
    }
    public TreeMap createIndexTable(){
        indexTree.clear();
        documents=database.getDocumentRepository().getDocuments();
        if (documents!=null) {
            documents.forEach((index, document) -> {
                System.out.println(document);
                JSONObject doc = document.getDocument();
                try {
                    if (doc.has(key)) {
                        ArrayList<Document> tmp =indexTree.getOrDefault(doc.get(key), new ArrayList<>());
                        tmp.add(document);
                        indexTree.put((Key) doc.get(key),tmp);
                    }
                    } catch(JSONException e){
                        throw new RuntimeException(e);
                    }
            });
        }
        createIndexFile();
        return indexTree;
    }

    private void createIndexFile(){
        String path="LFS/"+database.getUser().getUsername()+
                "/"+database.getDbName()+ "/"+key+"_Index.txt";
        FileSystem fileSystem = new FileSystemOperations();
        fileSystem.deleteFile(path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("LFS/"+database.getUser().getUsername()+
                "/"+database.getDbName()+ "/"+key+"_Index.txt", false))) {
            indexTree.forEach((key,value)-> {
                for (Document val:value) {
                    try {
                        writer.write("{" + key + ":" + val.getDocument().toString() + "}\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public TreeMap<Key, ArrayList<Document>> getIndexTree() {
        return indexTree;
    }

}
