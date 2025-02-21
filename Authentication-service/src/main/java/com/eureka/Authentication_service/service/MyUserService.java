package com.eureka.Authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eureka.Authentication_service.Model.MyUser;
import com.eureka.Authentication_service.repository.MyUserRepo;

@Service
public class MyUserService {
	 @Autowired
     ApplicationContext context;
    @Autowired
     MyUserRepo userrepo;
    @Autowired
    JwtService jwtservice;
    @Autowired
    AuthenticationManager authmanager;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);//(ROUNDS,TYPE{2A,2B,2Y})
    
	public MyUser addUserPassword(MyUser users) {
		users.setPassword(encoder.encode(users.getPassword()));
		MyUser u=userrepo.findByUsername(users.getUsername());
		 if (u!=null) {
	            throw new UserAlreadyExistsException("Username '" + users.getUsername() + "' is already taken!");
	        }
		 if (users.getRole() == null || users.getRole().isEmpty()) {
	            users.setRole("USER"); // Default role if not provided
	        }
		 
		userrepo.save(users);
		return users;
		
	}
	public String verify(MyUser myuser) {
		Authentication authentication=
				authmanager.authenticate(new UsernamePasswordAuthenticationToken(myuser.getUsername(),myuser.getPassword()));
		        if(authentication.isAuthenticated())
		        {
		        	return jwtservice.generateToken(myuser.getUsername());
		        }
		        else
		        {
		        	return "Failed";
		        }
	}
	public void validateToken(String token)
	{   String token1=token.substring(7);
		String username=jwtservice.extractUserName(token1);
	 UserDetails userDetails= context.getBean(MyUserDetailService.class).loadUserByUsername(username);
		jwtservice.validateToken(token1,userDetails);
	}

}