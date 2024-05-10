package com.example.testApplication.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.json.JSONObject;

import java.io.IOException;
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String password;
    private float balance;
    private int id;
    public User(){}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public User(String name,String password,float balance){
        this.name=name;
        this.password=password;
        this.balance=balance;
    }
    public User(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(jsonString, User.class);
            this.name = user.getName();
            this.password = user.getPassword();
            this.balance = user.getBalance();
        } catch (UnrecognizedPropertyException e) {
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public float getBalance() {
        return balance;
    }
    public JSONObject getDocument(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", name);
            jsonObject.put("password", password);
            jsonObject.put("balance", balance);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
    public JSONObject getSchema(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "string");
            jsonObject.put("password", "string");
            jsonObject.put("balance", "float");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
}
