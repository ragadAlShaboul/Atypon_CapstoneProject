package com.atypon.workerNode.data.repository;

import com.atypon.workerNode.data.model.Database;
import com.atypon.workerNode.fileSystem.FileSystem;
import com.atypon.workerNode.fileSystem.FileSystemOperations;
import com.atypon.workerNode.indexing.model.IndexTable;
import com.atypon.workerNode.indexing.respository.IndexRepository;
import com.atypon.workerNode.user.repository.UserRepository;
import org.json.JSONObject;

import java.io.File;
import java.util.TreeMap;

public class DatabaseRepository {
    private final TreeMap<String,Database> databases = new TreeMap<>();
    private final String username;
    private final String DB_PATH;
    private final FileSystem fileSystem= new FileSystemOperations();
    public DatabaseRepository(String username){
        this.username=username;
        this.DB_PATH = "LFS/"+username+"/";
        createUserDirectory();
    }
    public synchronized void createDatabase(String dbName,JSONObject schema){
        UserRepository userRepository = UserRepository.getUserRepository();
        createDatabaseDirectory(dbName);
        createSchemaFile(dbName,schema);
        Database database = new Database(schema,databases.size(),dbName,userRepository.getByUsername(username));
        databases.put(dbName,database);
        createIdIndex(database);
    }
    public synchronized boolean deleteDatabase(String dbName){
        databases.remove(dbName);
        return fileSystem.deleteDirectory(DB_PATH+dbName);
    }
    public void createSchemaFile(String dbName,JSONObject schema){
        String path = DB_PATH+dbName+"/"+dbName+"Schema.json";
        fileSystem.createFile(path);
        fileSystem.writeToFile(schema.toString(),path,false);
    }
    private void createDatabaseDirectory(String dbName){
        fileSystem.createDirectory(DB_PATH+dbName);
    }
    private void createUserDirectory(){
        File dir = new File(DB_PATH);
        if(dir.exists()){
            return;
        }
        dir.mkdir();
    }
    public Database getDatabaseByName(String dbName){
        return databases.get(dbName);
    }
    private IndexTable createIdIndex(Database database){
        IndexRepository indexRepository=new IndexRepository(database);
        return indexRepository.addIndexTable("id");
    }

    public TreeMap<String, Database> getDatabases() {
        return databases;
    }
}
