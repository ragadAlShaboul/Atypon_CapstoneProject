package com.atypon.workerNode;

import org.json.JSONObject;
import java.util.Map;

public class Request {
    private String dbName;
    private Map<String,Object> schema;
    private Map<String,Object> document;
    private String key;
    private Object value;
    private Object newValue;
    private String newKey;
    private int id;
    private String adminName;
    private String keyToRead;
    private String username;
    private String password;
    private String fromNode;
    private int affinityNum;
    public Request(){}

    public Request(Builder builder) {
        this.dbName = builder.dbName;
        this.key = builder.key;
        this.value = builder.value;
        this.newValue = builder.newValue;
        this.newKey = builder.newKey;
        this.id = builder.id;
        this.username = builder.username;
        this.fromNode = builder.fromNode;
        this.affinityNum=builder.affinityNum;
    }

    public String getAdminName(){return adminName;}
    public String getFromNode() {
        return fromNode;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getKeyToRead() {
        return keyToRead;
    }
    public Object getValue() {
        return value;
    }
    public int getId() {
        return id;
    }
    public Object getNewValue() {
        return newValue;
    }
    public String getNewKey() {
        return newKey;
    }
    public String getKey() {
        return key;
    }
    public String getDbName() {
        return dbName;
    }

    public int getAffinityNum() {
        return affinityNum;
    }

    public JSONObject getSchema() {
        if(schema!=null)
            return generateJSONObjectFromMap(schema);
        return null;
    }
    public JSONObject getDocument() {
        if(document!=null)
            return generateJSONObjectFromMap(document);
        return null;
    }
    public JSONObject generateJSONObjectFromMap(Map<String,Object> map){
        return new JSONObject(map);
    }
    @Override
    public String toString() {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"dbName\":\"" + dbName + '\"' +
                ", \"schema\":" + getSchema() +
                ", \"document\":" + getDocument() +
                ", \"key\":\"" + key + '\"' +
                ", \"value\":" +(value instanceof  String ?( "\"" + value + "\""):value) +
                ", \"newValue\":" +(newValue instanceof String?("\"" + newValue + "\""):newValue)  +
                ", \"newKey\":\"" + newKey + '\"' +
                ", \"id\":" + id +
                ", \"fromNode\":\""+fromNode+"\""+
                ", \"affinityNum\":"+affinityNum+
                '}';
    }
    public static class Builder {
        private final String dbName;
        private String key;
        private Object value;
        private Object newValue;
        private String newKey;
        private int id;
        private final String username;
        private final String fromNode;
        private int affinityNum;

        public Builder(String dbName, String username, String fromNode) {
            this.dbName = dbName;
            this.username = username;
            this.fromNode = fromNode;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(Object value) {
            this.value = value;
            return this;
        }

        public Builder newValue(Object newValue) {
            this.newValue = newValue;
            return this;
        }

        public Builder newKey(String newKey) {
            this.newKey = newKey;
            return this;
        }
        public Builder affinityNum(int affinityNum){
            this.affinityNum=affinityNum;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
