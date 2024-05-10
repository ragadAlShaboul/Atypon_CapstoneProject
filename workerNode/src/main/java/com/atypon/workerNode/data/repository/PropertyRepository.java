package com.atypon.workerNode.data.repository;

import com.atypon.workerNode.data.model.Document;
import com.atypon.workerNode.data.model.Property;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

public class PropertyRepository {
    private final TreeMap<String, Property> properties=new TreeMap<>();
    private final Document document;
    public PropertyRepository(Document document){
        this.document=document;
    }
    public Object readProperty(String key){
        updateProperties();
        if(properties.get(key)==null)
            return null;
        return properties.get(key).getValue();
    }
    public synchronized void deleteProperty(String key){
        updateProperties();
        if(!properties.containsKey(key))
            return;
        properties.remove(key,properties.get(key));
        document.setDocument(toJSONDocument());
        removeFromJSONSchema(key);
        updateDocument();
    }
    public synchronized void writeProperty(String key,Object value){
        updateProperties();
        if(!properties.containsKey(key))
            return;
        Property property = properties.get(key);
        property.setValue(value);
        properties.put(key,property);
        document.setDocument(toJSONDocument());
        updateDocument();
    }
    public synchronized void insertProperty(String key,Object value){
        updateProperties();
        if(properties.containsKey(key))
            return;
        Property property = new Property(key,value);
        properties.put(key,property);
        document.setDocument(toJSONDocument());
        addToJSONSchema(key, getType(value));
        updateDocument();
    }
    private synchronized void updateProperties(){
        System.out.println(document.getDocument());
        System.out.println(properties.toString());
        JSONObject doc=document.getDocument();
        JSONArray names=doc.names();
        for (int i=0;i<names.length();i++){
            try {
                String name =(String) names.get(i);
                properties.put(name,new Property(name,doc.get(name)));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(document.getDocument());
        System.out.println(properties.toString());
    }
    public synchronized JSONObject toJSONDocument(){
        JSONObject doc = new JSONObject();
        properties.forEach((s,i)-> {
            try {
                doc.put(s,i.getValue());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
        return doc;
    }
    private synchronized void updateDocument(){
        DocumentRepository documentRepository=document.getDatabase().getDocumentRepository();
        try {
            documentRepository.updateDocument(document);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void removeFromJSONSchema(String key){
        JSONObject schema=document.getSchema();
        schema.remove(key);
        document.setSchema(schema);
    }
    public void addToJSONSchema(String key,String type){
        JSONObject schema=document.getSchema();
        try {
            schema.put(key,type);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        document.setSchema(schema);
    }
    private String getType(Object value){
        if(value instanceof String)
            return "string";
        else if (value instanceof Boolean)
            return "boolean";
        else if(value instanceof Integer)
            return "int";
        else if(value instanceof JSONObject)
            return "JSONObject";
        else if(value instanceof JSONArray)
            return "JSONArray";
        else return "";
    }
    public Document getDocument() {
        return document;
    }
}
