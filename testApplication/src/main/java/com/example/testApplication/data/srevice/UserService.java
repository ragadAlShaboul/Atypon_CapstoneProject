package com.example.testApplication.data.srevice;

import com.example.testApplication.data.dao.DAO;
import com.example.testApplication.data.dao.DaoV1;
import com.example.testApplication.data.model.User;

import java.io.IOException;
import java.util.List;

public class UserService {
    private DAO dao = new DaoV1();
    private String dbName = "users";
    public User getUser(String username){
        List<String> users =dao.getDocumentsWhere("users","username",username);
        try {
            return new User(users.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean userExists(String username,String password){
        List<Object> users = dao.getDocumentsWhere(dbName,"username",username);
        if(!users.isEmpty()) {
            try {
                return new User((String) users.get(0)).getPassword().equals(password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    public float deposit(String username,float amount){
        List<String> users = dao.getDocumentsWhere(dbName,"username",username);
        System.out.println(users.get(0));
        User user = null;
        try {
            user = new User(users.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        float balance = user.getBalance()+amount;
        dao.updatePropertyWhere(dbName,"username",username,"balance",balance);
        System.out.println(balance);
        return balance;
    }
    public float withdraw(String username,float amount){
        List<String> users = dao.getDocumentsWhere(dbName,"username",username);
        User user = null;
        try {
            user = new User(users.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        float balance = user.getBalance()-amount;
        if(balance>=0) {
            dao.updatePropertyWhere(dbName, "username", username, "balance", balance);
            return balance;
        }
        return -1;
    }
    public float getBalance(String username){
        List<String> users = dao.getDocumentsWhere(dbName,"username",username);
        User user = null;
        try {
            user = new User(users.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user.getBalance();
    }
}
