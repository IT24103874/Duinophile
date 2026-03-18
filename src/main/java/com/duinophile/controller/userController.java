package com.duinophile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class userController {
    @GetMapping("/")
    public String indexLoad(){
        return "IndexPage";
    }

    @PostMapping("/users/login")
    public String login(@RequestParam String username, @RequestParam String password){
        if (username.equals("Admin") && password.equals("Password")){
            return "courses";
        }
        return "IndexPage";
    }
}
