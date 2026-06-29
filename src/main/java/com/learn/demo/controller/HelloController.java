package com.learn.demo.controller;

import com.learn.demo.model.User;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id) {
        return new User("Dnyanesh", "Java Developer", id);
    }

    @PostMapping("/user")
    public String createUser(@RequestBody User user) {
        System.out.println("Received: " + user.getName() + ", " + user.getRole());
        return "User created: " + user.getName();
    }
}