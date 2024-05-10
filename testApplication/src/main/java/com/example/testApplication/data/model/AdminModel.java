package com.example.testApplication.data.model;


import java.util.ArrayList;
import java.util.List;

public class AdminModel {
    private List<String> users=new ArrayList<String>();
    private List<String> employees=new ArrayList<String>();
    private String name;
    private String message;

    public AdminModel(List<String> users, List<String> employees, String name,String message) {
        this.users = users;
        this.employees = employees;
        this.name = name;
        this.message=message;
    }

    public List<String> getUsers() {
        return users;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public String getName() {
        return name;
    }
}
