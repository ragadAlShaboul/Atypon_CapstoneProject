package com.atypon.workerNode.user.repository;

import com.atypon.workerNode.fileSystem.FileSystem;
import com.atypon.workerNode.fileSystem.FileSystemOperations;
import com.atypon.workerNode.user.model.User;

import java.io.*;
import java.util.TreeMap;

public class UserRepository {
    private final TreeMap<String, User> users = new TreeMap<>();
    private static final UserRepository userRepository = new UserRepository();
    private final String FILE_PATH = "LFS/users.txt";
    private final FileSystem fileSystem=new FileSystemOperations();

    private UserRepository() {
        createUserDir();
    }
    public synchronized void createUserDir(){
        String path ="LFS/";
        fileSystem.createDirectory(path);
        fileSystem.createFile(FILE_PATH);
    }
    public synchronized void addNewUser(User user){
        users.put(user.getUsername(),user);
        fileSystem.writeToFile(user.toString(),FILE_PATH,true);
    }
    public static UserRepository getUserRepository() {
        return userRepository;
    }
    public User getByUsername(String username){
        if(users.containsKey(username))
            return users.get(username);
        return null;
    }
    public TreeMap<String, User> getUsers() {
        return users;
    }
}
