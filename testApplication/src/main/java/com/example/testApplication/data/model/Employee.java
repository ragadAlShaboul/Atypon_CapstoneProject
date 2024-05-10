package com.example.testApplication.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.json.JSONObject;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    private String name;
    private String password;
    public Employee(){}
    public Employee(String name, String password){
        this.name=name;
        this.password=password;
    }
    public Employee(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Employee employee = objectMapper.readValue(jsonString, Employee.class);
            this.name = employee.getName();
            this.password = employee.getPassword();
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
    public JSONObject getDocument(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", name);
            jsonObject.put("password", password);
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
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
}
