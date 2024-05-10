package com.example.testApplication.data.srevice;

import com.example.testApplication.data.dao.DAO;
import com.example.testApplication.data.dao.DaoV1;
import com.example.testApplication.data.model.Employee;
import com.example.testApplication.data.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class EmployeeService {
    DAO dao =new DaoV1();
    ObjectMapper objectMapper = new ObjectMapper();

    public boolean employeeExists(String username, String password) {
        List<String> employees = dao.getDocumentsWhere("employees", "username", username);
        if (!employees.isEmpty()) {
            try {
                return new Employee(employees.get(0)).getPassword().equals(password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }
    public boolean userExists(String username){
        List<String> users=dao.getDocumentsWhere("users","username",username);
        return !users.isEmpty();
    }

    public boolean addUser(String username,String password,float balance){
        User user = new User(username,password,balance);
        return dao.createDocument("users",user.getDocument(),user.getSchema());
    }
    public float getBalance(String username){
        float balance =  Float.parseFloat((String) dao.getPropertyWhere("users","username",username,"balance").get(0));
        return balance;
    }

}

