package com.example.testApplication.data.dao;

import org.json.JSONObject;

import java.util.List;

public interface DAO {
    public String createNewUser();
    public boolean createConnection();
    public boolean createDatabase(String dbName, JSONObject schema);
    public boolean deleteDatabase(String dbName);
    public boolean createDocument(String dbName,JSONObject document,JSONObject documentSchema);
    public boolean deleteDocumentWhere(String key,String dbName);
    public boolean insertPropertyWhere(String dbName,String key,String value,String newKey,String newValue);
    public boolean removePropertyWhere(String dbName,String key,String value);
    public boolean updatePropertyWhere(String dbName,String key,Object value,String newKey,Object newValue) ;
    public List getPropertyWhere(String dbName, String key, String value, String keyToRead);
    public List getDocumentsWhere(String dbName,String key,String value);
    public List getAllDocuments(String dbName);
}
