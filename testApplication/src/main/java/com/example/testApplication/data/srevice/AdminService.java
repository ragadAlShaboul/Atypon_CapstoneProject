package com.example.testApplication.data.srevice;


import com.example.testApplication.data.dao.DAO;
import com.example.testApplication.data.dao.DaoV1;
import com.example.testApplication.data.model.Employee;
import com.example.testApplication.data.model.User;

import java.util.List;

public class AdminService {
    DAO dao = new DaoV1();
    public boolean checkAdmin(String username,String password){
        return username.equals("admin") && password.equals("admin");
    }
    public List<String> getAllUsers(){
        return dao.getAllDocuments("users");
    }
    public List<String> getAllEmployees(){
        return dao.getAllDocuments("employees");
    }
    public boolean addEmployee(String username,String password){
        Employee employee = new Employee(username,password);
        return dao.createDocument("employees", employee.getDocument(), employee.getSchema());
    }
    public boolean addUser(String username,String password,float balance){
        User user = new User(username,password,balance);
        return dao.createDocument("users",user.getDocument(),user.getSchema());
    }
}
