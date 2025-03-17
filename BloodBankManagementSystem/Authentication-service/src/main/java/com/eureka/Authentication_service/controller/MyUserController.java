package com.eureka.Authentication_service.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eureka.Authentication_service.Model.MyUser;
import com.eureka.Authentication_service.service.JwtService;
import com.eureka.Authentication_service.service.MyUserService;

@Component
@RestController
@RequestMapping("/auth")
public class MyUserController {

	@Autowired
	MyUserService userservice;
	@Autowired
    private JwtService jwtService;
	
	  @PostMapping("/adduser") 
	  public ResponseEntity<MyUser> addUser(@RequestBody MyUser user) {
		  	  return ResponseEntity.ok(userservice.addUserPassword(user));
		 
		}
	  @PostMapping("/login")
	  public String Login(@RequestBody MyUser myuser)
	  {
		  return userservice.verify(myuser);
	  }
	  @PostMapping("/validatetoken")
	    public String validateToken(@RequestHeader("Authorization") String Token)
    {    
		  userservice.validateToken(Token);
		  System.out.println("success");
		     return "Successful";
	    }
	  @PostMapping("/userrole")
	    public ResponseEntity<String> userrole(@RequestHeader("Authorization") String token)
	    {
		  String role = jwtService.extractUserRole(token);
	        return ResponseEntity.ok(role);
	    }
	  @PostMapping("/refresh-token")   // refresh the token based on old token
	    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String oldToken) {
	        // Extract the token (remove "Bearer " prefix)
	        if (oldToken != null && oldToken.startsWith("Bearer ")) {
	            oldToken = oldToken.substring(7);
	        } else {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format!");
	        }
	        // Generate new token
	        return ResponseEntity.ok(jwtService.refreshToken(oldToken));
	    }
	  @PostMapping("/uploadUsers")
	  public ResponseEntity<?> uploadUsers(@RequestParam("file") MultipartFile file) {
	      List<MyUser> users = new ArrayList<>();
	      
	      try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	          String line;
	          boolean firstLine = true;
	          
	          while ((line = br.readLine()) != null) {
	              if (firstLine) { // Skip CSV header
	                  firstLine = false;
	                  continue;
	              }
	              
	              String[] data = line.split(","); 
	              if (data.length < 2) continue; // Ensure at least username & password exist

	              MyUser user = new MyUser();
	              user.setUsername(data[0].trim()); // Trim spaces
	              user.setPassword(data[1].trim());
	              user.setRole(data.length > 2 && !data[2].isEmpty() ? data[2].trim() : "USER"); 

	              users.add(user);
	          }
	          
	          // Attempt to add users
	          List<MyUser> savedUsers = userservice.addUsersByCsv(users);
	          if(savedUsers.isEmpty())
	          {
	        	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File is failed to processing bcs duplication found!");
	          }
	          return ResponseEntity.ok(savedUsers);
	          
	      } catch (RuntimeException e) {
	          return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + e.getMessage());
	      } catch (Exception e) {
	          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing failed!");
	      }
	      
	  }

}
