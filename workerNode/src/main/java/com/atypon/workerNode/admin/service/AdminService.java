package com.atypon.workerNode.admin.service;

import com.atypon.workerNode.authentication.AdminAuthenticator;
import com.atypon.workerNode.authentication.Authenticator;
import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final UserRepository userRepository = UserRepository.getUserRepository();

    public boolean adminExists(String username,String password){
        Authenticator authenticator=new AdminAuthenticator();
        return authenticator.authenticate(username,password);
    }
    public String getAllUsers(){
        return userRepository.getUsers().toString();
    }
    public String getAlDatabases(String username){
        User user = userRepository.getByUsername(username);
        return user.getDatabaseRepository().getDatabases().toString();
    }
}
