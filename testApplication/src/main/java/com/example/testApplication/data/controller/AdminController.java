package com.example.testApplication.data.controller;

import com.example.testApplication.data.model.AdminModel;
import com.example.testApplication.data.model.Employee;
import com.example.testApplication.data.model.User;
import com.example.testApplication.data.srevice.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    AdminService adminService = new AdminService();
    @RequestMapping("/addUser")
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password
            , @RequestParam("role")String role,@RequestParam("hidden")String hidden, Model model){
        String message = "error occurred";
        if(!username.isEmpty() && !password.isEmpty() && !role.isEmpty()){
        if(role.equals("user")){
            adminService.addUser(username,password,0);
            message = "added successfully";
        } else if (role.equals("employee")) {
            adminService.addEmployee(username,password);
            message = "added successfully";
        }}
        model.addAttribute("adminModel",new AdminModel(new ArrayList<>(),new ArrayList<>(),hidden,message));
        return "admin";
    }
    @RequestMapping("/getUsers")
    public String getUsers(@RequestParam("hidden") String hidden,Model model){
        List<String> users = adminService.getAllUsers();
        model.addAttribute("adminModel",new AdminModel(users,new ArrayList<>(),hidden,""));
        return "admin";
    }
    @RequestMapping("/getEmployees")
    public String getEmployees(@RequestParam("hidden") String hidden,Model model){
        List<String> employees = adminService.getAllEmployees();
        model.addAttribute("adminModel",new AdminModel(new ArrayList<>(),employees,hidden,""));
        return "admin";
    }
}
