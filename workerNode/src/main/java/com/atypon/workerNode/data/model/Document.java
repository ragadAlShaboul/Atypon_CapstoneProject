package com.atypon.workerNode.data.model;

import com.atypon.workerNode.cluster.model.Node;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.data.repository.PropertyRepository;
import com.atypon.workerNode.data.utils.ValidationStrategy;
import com.atypon.workerNode.data.utils.Validator;
import org.json.JSONException;
import org.json.JSONObject;

public class Document {
    private JSONObject document;
    private JSONObject schema;
    private int id;
    private Database database;
    private ValidationStrategy validationStrategy;
    private PropertyRepository propertyRepository;
    private Node affinity;
    public Document(int id, JSONObject document, JSONObject schema, Database database,Node affinity) {
        this.document = document;
        this.schema = schema;
        this.id=id;
        this.database=database;
        this.affinity=affinity;
        setValidationStrategy(new Validator());
        this.propertyRepository=new PropertyRepository(this);
    }
    protected void setValidationStrategy(ValidationStrategy validationStrategy) {
        this.validationStrategy = validationStrategy;
    }
    public boolean validate(){
        try {
            return validationStrategy.validate(schema,document);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean hasAffinity(){
        return getAffinity().equals(NodeRepository.getCurrentNode());
    }
    public void insertProperty(String newKey,Object newValue){
        propertyRepository.insertProperty(newKey,newValue);
    }
    public void writeProperty(String key,Object newValue){
        propertyRepository.writeProperty(key,newValue);
    }
    public void deleteProperty(String key){
        propertyRepository.deleteProperty(key);
    }
    public Object readProperty(String key){
        return propertyRepository.readProperty(key);
    }

    public String getAffinity() {
        return affinity.getURL();
    }
    public Node getAffinityNode(){return affinity;}
    public Database getDatabase() {
        return database;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public JSONObject getDocument() {
        return document;
    }
    public void setDocument(JSONObject document) {
        this.document = document;
    }
    public JSONObject getSchema() {
        return schema;
    }
    public void setSchema(JSONObject schema) {
        this.schema = schema;
    }
}
