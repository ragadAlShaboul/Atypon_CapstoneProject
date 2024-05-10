package com.example.testApplication.data.controller;

import com.example.testApplication.data.model.EmployeeModel;

import com.example.testApplication.data.srevice.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {
    EmployeeService employeeService = new EmployeeService();
    @RequestMapping("/addUserFromEmployee")
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password,
                          @RequestParam("balance") float balance, @RequestParam("hidden") String hidden,
                          Model model){
        String message = "error occurred";
        if(!username.isEmpty() && !password.isEmpty()) {
            if(!employeeService.userExists(username))
                if(employeeService.addUser(username, password, balance))
                    message = "added successfully";
        }
        model.addAttribute("employeeModel",new EmployeeModel(0,hidden,message,""));
        return "employee";
    }
    @RequestMapping("/getBalance")
    public String getBalance(@RequestParam("username") String username, @RequestParam("hidden") String hidden,
                             Model model){
        String message = "user doesn't exist";
        float balance = 0;
        if(employeeService.userExists(username)) {
            balance = employeeService.getBalance(username);
            message = "done";
        }
        model.addAttribute("employeeModel",new EmployeeModel(balance,hidden,"",message));
        return "employee";
    }
}
