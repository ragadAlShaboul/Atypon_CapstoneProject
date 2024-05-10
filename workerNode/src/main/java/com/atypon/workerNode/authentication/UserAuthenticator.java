package com.atypon.workerNode.authentication;

import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.cluster.repository.NodeRepository;
import com.atypon.workerNode.user.repository.UserRepository;

import java.util.TreeMap;

public class UserAuthenticator implements Authenticator{
    private final UserRepository userRepository = UserRepository.getUserRepository();
    private final TreeMap<String, User> users = userRepository.getUsers();

    public boolean authenticate(String username,String password){
        if(users.containsKey(username)) {
            User user = getUser(username);
            return checkPassword(user,password) && checkNode(user);
        }
        return false;
    }
    private User getUser(String username){
        return users.get(username);
    }
    private boolean checkPassword(User user,String password){
        return user.getPassword().equals(password);
    }
    private boolean checkNode(User user){
        String currentNode = NodeRepository.getCurrentNode();
        return user.getCommNode().equals(currentNode);
    }
}
