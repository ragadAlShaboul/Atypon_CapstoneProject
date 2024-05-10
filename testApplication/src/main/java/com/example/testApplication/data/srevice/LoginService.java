package com.example.testApplication.data.srevice;


import com.example.testApplication.data.model.User;

public class LoginService {
    AdminService adminService = new AdminService();
    UserService userService = new UserService();
    EmployeeService employeeService = new EmployeeService();
    public String getUserRole(String username,String password){
        if(adminService.checkAdmin(username,password))
            return "admin";
        if(employeeService.employeeExists(username,password))
            return "employee";
        if (userService.userExists(username,password))
            return "user";
        return "login";
    }
    public User getUser(String name){
        return userService.getUser(name);
    }
}
