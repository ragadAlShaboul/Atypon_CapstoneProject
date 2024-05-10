package com.atypon.workerNode.user.service;

import com.atypon.workerNode.authentication.Authenticator;
import com.atypon.workerNode.authentication.UserAuthenticator;
import com.atypon.workerNode.user.model.User;
import com.atypon.workerNode.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository = UserRepository.getUserRepository();
    public boolean userExists(String username,String password){
        Authenticator authenticator = new UserAuthenticator();
        return authenticator.authenticate(username,password);
    }
    public void addNewUser(User user){
        userRepository.addNewUser(user);
    }

}
