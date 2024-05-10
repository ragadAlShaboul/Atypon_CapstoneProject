package com.atypon.bootstrappingNode.user.repository;

import com.atypon.bootstrappingNode.user.model.User;
import com.atypon.bootstrappingNode.user.service.UserService;
import org.json.JSONObject;


import java.io.*;
import java.util.TreeMap;

public class UsersRepository {
    private static final UsersRepository usersRepository = new UsersRepository();
    private static final String FILE_PATH = "LFS/users.txt";
    private static final TreeMap<String, User> users=new TreeMap<>();
    private UsersRepository(){
        createUsersFile();
    }
    public boolean userExists(String username,String password){
        if(users.containsKey(username))
            return users.get(username).getPassword().equals(password);
        return false;
    }
    public static void updateUsersList(){
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(line);
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                if(getByUsername(username)==null) {
                    User user = new User(username, password);
                    users.put(username,user);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    public static User getByUsername(String username){
        if(users.containsKey(username))
            return users.get(username);
        return null;
    }
    public boolean userExists(String username){
        return users.containsKey(username);
    }
    public synchronized void writeUserToSystem(User user){
        users.put(user.getUsername(),user);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendUsersInfo(){
        UserService userService = new UserService();
        users.forEach((key,value)->{userService.sendUserInformation(value);});
    }
    public static UsersRepository getUsersRepository(){
        return usersRepository;
    }
    private void createUsersFile(){
        File dir =new File("LFS/");
        dir.mkdir();
        File file = new File(FILE_PATH);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
