package com.example.SpringbootExercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringbootExercise.models.MyUser;
import com.example.SpringbootExercise.services.MyUserService;
@Component
@RestController
public class MyUserController {

	@Autowired
	MyUserService userservice;

	
	  @PostMapping("/adduser") 
	  public MyUser addUser(@RequestBody MyUser user) {
		  return userservice.addUserPassword(user);
		 
		}
}
