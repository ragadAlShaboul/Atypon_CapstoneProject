package com.example.testApplication.data.controller;

import com.example.testApplication.data.model.UserModel;
import com.example.testApplication.data.srevice.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    UserService userService = new UserService();
    @RequestMapping("/deposit")
    public String deposit(@RequestParam("amount") float amount, @RequestParam("hidden") String hidden,
                          Model model){
        String message = "done";
        if(amount==0)
            message = "please enter non zero number";
        float balance = userService.deposit(hidden,amount);
        model.addAttribute("userModel", new UserModel(hidden,message,"",balance));
        return "user";
    }
    @RequestMapping("/withdraw")
    public String withdraw(@RequestParam("amount") float amount, @RequestParam("hidden") String hidden,
                           Model model){
        float balance = userService.withdraw(hidden,amount);
        String message = "done";
        if(balance==-1 || amount==0) {
            balance = userService.getBalance(hidden);
            message = "error occurred";
        }
        model.addAttribute("userModel", new UserModel(hidden,"",message,balance));
        return "user";
    }
}
