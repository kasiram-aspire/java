package com.example.SpringbootExercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringbootExercise.models.MyUser;
import com.example.SpringbootExercise.services.JwtService;
import com.example.SpringbootExercise.services.MyUserService;

@Component
@RestController
public class MyUserController {

	@Autowired
	MyUserService userservice;
	@Autowired
    private JwtService jwtService;
	
	  @PostMapping("/adduser") 
	  public MyUser addUser(@RequestBody MyUser user) {
		  return userservice.addUserPassword(user);
		 
		}
	  @PostMapping("/login")
	  public String Login(@RequestBody MyUser myuser)
	  {
		  return userservice.verify(myuser);
	  }
	  @PostMapping("/refresh-token")   // refresh the token based on old token
	    public String refreshToken(@RequestHeader("Authorization") String oldToken) {
	        // Extract the token (remove "Bearer " prefix)
	        if (oldToken != null && oldToken.startsWith("Bearer ")) {
	            oldToken = oldToken.substring(7);
	        } else {
	            return "Invalid token format!";
	        }

	        // Generate new token
	        return jwtService.refreshToken(oldToken);
	    }
}
