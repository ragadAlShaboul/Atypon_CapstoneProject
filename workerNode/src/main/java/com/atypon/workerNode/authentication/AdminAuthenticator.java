package com.atypon.workerNode.authentication;

public class AdminAuthenticator implements Authenticator{
    public boolean authenticate(String username,String password){
        return username.equals("admin")&&password.equals("admin");
    }
}
