package com.example.SpringbootExercise.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringbootExercise.models.MyUser;
import com.example.SpringbootExercise.repository.MyUserRepo;

@Service
public class MyUserService {
    @Autowired
     MyUserRepo userrepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);//(ROUNDS,TYPE{2A,2B,2Y})
	public MyUser addUserPassword(MyUser users) {
		users.setPassword(encoder.encode(users.getPassword()));
		userrepo.save(users);
		return users;
		
	}

}
