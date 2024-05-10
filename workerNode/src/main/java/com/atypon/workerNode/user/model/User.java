package com.atypon.workerNode.user.model;

import com.atypon.workerNode.data.repository.DatabaseRepository;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private final String username;
    private final String password;
    private final DatabaseRepository databaseRepository;
    private final String commNode;

    public User(String username, String password,String commNode) {
        this.username = username;
        this.password = password;
        this.commNode = commNode;
        databaseRepository=new DatabaseRepository(username);
    }
    public String getCommNode(){
        return commNode;
    }
    public DatabaseRepository getDatabaseRepository() {
        return databaseRepository;
    }


    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public static User toObject(String user) throws JSONException {
        JSONObject jsonObject = new JSONObject(user);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String commNode = jsonObject.getString("commNode");
        return new User(username, password,commNode);
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", commNode='"+commNode+'\''+
                '}';
    }
}
