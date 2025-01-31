package com.example.SpringbootExercise.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SpringbootExercise.models.GrocessryProduct;
import com.example.SpringbootExercise.models.User;
import com.example.SpringbootExercise.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userrepo;
	public List<User> getAllproducts() {
		// TODO Auto-generated method stub
		return userrepo.findAll();
	}
	

}
