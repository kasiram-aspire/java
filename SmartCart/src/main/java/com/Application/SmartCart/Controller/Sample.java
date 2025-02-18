package com.Application.SmartCart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Application.SmartCart.Model.User;
import com.Application.SmartCart.Repository.UserRepository;
import com.Application.SmartCart.Service.Userservice;

@RestController
public class Sample {
	
	@Autowired
	 private Userservice userservice;
   @PostMapping("/add")
   public User adduser(@RequestBody User user)
   {
	return userservice.adduser(user);
	   
   }
   
}
