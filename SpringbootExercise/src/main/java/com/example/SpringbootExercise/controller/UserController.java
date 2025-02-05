package com.example.SpringbootExercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringbootExercise.models.User;
import com.example.SpringbootExercise.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to save a User with Grocery Products
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUserWithProducts(user);
    }

    // Endpoint to get all Users with Grocery Products
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public User getUsers(@PathVariable Long id) {
        return userService.getById(id);
    }
    
}