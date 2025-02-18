package com.Application.SmartCart.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Application.SmartCart.Model.User;
import com.Application.SmartCart.Repository.UserRepository;

@Service
public class Userservice {
	@Autowired
	  private UserRepository userrepo;
	public User adduser(User user) {
		 return userrepo.save(user);
		
	}

}
