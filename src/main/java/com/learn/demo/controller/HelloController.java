package com.learn.demo.controller;

import com.learn.demo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello!! API is working!";
    }

    @GetMapping("/user")
    public User getUser() {
        return new User("Dnyanesh Pise", "Java Developer", 8);
    }
}