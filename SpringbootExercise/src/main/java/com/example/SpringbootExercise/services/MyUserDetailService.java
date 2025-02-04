package com.example.SpringbootExercise.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.SpringbootExercise.models.MyUser;
import com.example.SpringbootExercise.models.UserPrincipals;
import com.example.SpringbootExercise.repository.MyUserRepo;

@Service
public class MyUserDetailService implements UserDetailsService{
    @Autowired
     private MyUserRepo repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 MyUser user=repo.findByUsername(username);
		 if(user == null)
		 {
			 System.out.println("User not found");
			 throw new UsernameNotFoundException("User not found");
		 }
		return new UserPrincipals(user);
	}

}
