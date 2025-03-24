package com.eureka.Authentication_service.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
		if(users.getRole().equalsIgnoreCase("ADMIN") || users.getRole().equalsIgnoreCase("SuperAdmin"))
		{
			throw new UserAlreadyExistsException("role:"+users.getRole()+" cant add manually");
		}
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

	public List<MyUser> addUsersByCsv(List<MyUser> users) {
	    List<MyUser> savedUsers = new ArrayList<>();
	    
	    for (MyUser user : users) {
	        MyUser existingUser = userrepo.findByUsername(user.getUsername());

	        if (existingUser != null) {
	            System.out.println("Duplicate user skipped: " + user.getUsername()); // Log instead of throwing
	            continue;
	        }

	        user.setPassword(encoder.encode(user.getPassword()));
	        savedUsers.add(userrepo.save(user));
	    }
	    return savedUsers;
	}
	public ResponseEntity<?> uploadUser(MultipartFile file) throws IOException {
		List<MyUser> users = new ArrayList<>();
	    
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	        String line;
	        boolean firstLine = true;

	        while ((line = br.readLine()) != null) {
	            if (firstLine) {
	                firstLine = false;  // Skip header
	                continue;
	            }

	            String[] data = line.split(",");
	            if (data.length < 2) continue; // Skip invalid rows

	            MyUser user = new MyUser();
	            user.setUsername(data[0].trim());
	            user.setPassword(data[1].trim());
	            user.setRole(data.length > 2 && !data[2].isEmpty() ? data[2].trim() : "USER");

	            users.add(user);
	        }
	    }

	
	    List<MyUser> savedUsers = addUsersByCsv(users);


	    if (savedUsers.isEmpty()) {
	    	 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File processing failed due to duplicate users!");
	    }


	    return ResponseEntity.ok(savedUsers);
	}

}