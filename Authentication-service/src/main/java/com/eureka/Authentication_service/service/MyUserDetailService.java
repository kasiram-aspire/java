package com.eureka.Authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eureka.Authentication_service.Model.MyUser;
import com.eureka.Authentication_service.Model.UserPrincipals;
import com.eureka.Authentication_service.repository.MyUserRepo;


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
