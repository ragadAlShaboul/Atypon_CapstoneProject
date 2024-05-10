package com.atypon.bootstrappingNode.user.service;

import com.atypon.bootstrappingNode.cluster.communication.ClusterBroadcast;
import com.atypon.bootstrappingNode.cluster.model.Message;
import com.atypon.bootstrappingNode.cluster.model.Node;
import com.atypon.bootstrappingNode.user.repository.UsersRepository;
import com.atypon.bootstrappingNode.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UsersRepository usersRepository = UsersRepository.getUsersRepository();
    public void assignNewUser(User user){
        usersRepository.writeUserToSystem(user);
    }
    public boolean userExists(String username){
        return usersRepository.userExists(username);
    }
    private Node getUserNode(String username){
        return getByUsername(username).getCommNode();
    }
    public void sendUserInformation(User user){
        Message message=new Message(user,"/userCredentials");
        ClusterBroadcast.send(message);
    }
    private User getByUsername(String username){
        return UsersRepository.getByUsername(username);
    }
    public boolean userExists(String username,String password){
        return usersRepository.userExists(username,password);
    }
    public String getUserURL(String username){
        return getUserNode(username).getUserURL();
    }
}
