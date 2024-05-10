package com.atypon.workerNode.indexing.respository;

import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.indexing.model.IndexTable;

import java.util.TreeMap;

public class IndexRepository {
    private final TreeMap<String, IndexTable> searchTree =new TreeMap<>();
    private Database database;

    public IndexRepository(Database database){
        this.database=database;
    }
    public IndexTable addIndexTable(String key){
        searchTree.put(key,new IndexTable<>(key,database));
        return searchTree.get(key);
    }
    public IndexTable getIndexOn(String key){
        if(hasIndexOn(key)) {
            updateIndexTable(key);
            return searchTree.get(key);
        }
        else return null;
    }
    public void updateIndexTables(){
        searchTree.forEach((key,value)->{
            value.createIndexTable();
        });
    }
    private void updateIndexTable(String key){
        searchTree.get(key).createIndexTable();
    }
    public boolean hasIndexOn(String key){
        return searchTree.containsKey(key);
    }
}
