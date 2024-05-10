package com.atypon.workerNode.authentication;

public interface Authenticator {
    public boolean authenticate(String username,String password);
}
