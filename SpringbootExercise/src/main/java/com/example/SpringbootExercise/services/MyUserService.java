package com.example.SpringbootExercise.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.SpringbootExercise.models.MyUser;
import com.example.SpringbootExercise.repository.MyUserRepo;

@Service
public class MyUserService {
    @Autowired
     MyUserRepo userrepo;
    @Autowired
    JwtService jwtservice;
    @Autowired
    AuthenticationManager authmanager;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);//(ROUNDS,TYPE{2A,2B,2Y})
    
	public MyUser addUserPassword(MyUser users) {
		users.setPassword(encoder.encode(users.getPassword()));
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

}
