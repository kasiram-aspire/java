package com.bloodBank.User_Service.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloodBank.User_Service.Model.Donors;
import com.bloodBank.User_Service.Service.DonorService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user/donor")
public class DonorContoller {
	private static final Logger log = LoggerFactory.getLogger(DonorContoller.class);
	@Autowired 
	DonorService donorservice;
    
	@PostMapping("/addDonor")   // add donor to donor data base
	public ResponseEntity<Donors> addDonor(@RequestHeader("X-User-Role") String role,@RequestBody Donors donor)
	{     
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	      log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
		 log.info("Received request to add donor: {}", donor);   
	     Donors savedDonor = donorservice.addDonors(donor);
	     log.info("Donor added successfully: {}", savedDonor);
	     return ResponseEntity.ok(savedDonor);

	}
	@GetMapping("/getDonor")     // get donor from donor database 
	public ResponseEntity<List<Donors>> getDonors(@RequestHeader("X-User-Role") String role)
	{   

        log.info("Received request to fetch all donors.");
		if (!"ADMIN".equalsIgnoreCase(role)) {
        log.warn("Unauthorized access attempt by role: {}", role);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
         }
        List<Donors> donors = donorservice.getDonors();
        log.info("Returning {} donors", donors.size());
		 return ResponseEntity.ok(donors);
	}
	@GetMapping("/getDonorbyId/{id}")  // get the donor by id
	public ResponseEntity<Donors> getDonorById(@RequestHeader("X-User-Role") String role,@PathVariable Long id)
	{   
        log.info("Received request to fetch donor with ID: {}", id);
		if (!"ADMIN".equalsIgnoreCase(role)) {
        log.warn("Unauthorized access attempt by role: {}", role);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
         }
		Donors donor = donorservice.getById(id);
        log.info("Returning donor: {}", donor);
		 return ResponseEntity.ok(donor);
	}
	@PostMapping("/update")   // update the donor DB
	public ResponseEntity<Donors> updateDonor(@RequestHeader("X-User-Role") String role,@RequestBody Donors donor)
	{  
		log.info("Received request to update donor: {}", donor);
		if (!"ADMIN".equalsIgnoreCase(role)) {
        log.warn("Unauthorized access attempt by role: {}", role);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
         }

        Donors updatedDonor = donorservice.update(donor);
        log.info("Donor updated successfully: {}", updatedDonor);
        return ResponseEntity.ok(updatedDonor);
	}
	@PostMapping("/getByBloodGrouName/{Bloodgroup}")  //get donor by blood group
	public ResponseEntity<List<Donors>> getDonorByBloodGroupName(@RequestHeader("X-User-Role") String role,@PathVariable String Bloodgroup)
	{   
        log.info("Received request to fetch donors with blood group: {}", Bloodgroup);
		if (!"ADMIN".equalsIgnoreCase(role)) {
        log.warn("Unauthorized access attempt by role: {}", role);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
         }
		List<Donors> donors = donorservice.getDonorByBloodGroupName(Bloodgroup);
        log.info("Returning {} donors with blood group: {}", donors.size(), Bloodgroup);
        return ResponseEntity.ok(donors);
	}
	@PostMapping("/getByBloodGrouNameAndAge/{Bloodgroup}/{Age}") // get by blood group and age
	public ResponseEntity<List<Donors>> getDonorByBloodGroupNameAndAge(@RequestHeader("X-User-Role") String role,@PathVariable String Bloodgroup,@PathVariable Integer Age)
	{   
		log.info("Received request to fetch donors with blood group: {} and age: {}", Bloodgroup, Age);
		if (!"ADMIN".equalsIgnoreCase(role)) {
        log.warn("Unauthorized access attempt by role: {}", role);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
         }
        List<Donors> donors = donorservice.getDonorByBloodGroupNameAndAge(Bloodgroup, Age);
        log.info("Returning {} donors with blood group: {} and age: {}", donors.size(), Bloodgroup, Age);
        return ResponseEntity.ok(donors);

	}
	@GetMapping("/getByName/{name}") // get donor by donor name
	public ResponseEntity<Donors> getDonorByBloodGroupNameAndAge(@RequestHeader("X-User-Role") String role,@PathVariable String name)
	{   
        log.info("Received request to fetch donor by name: {}", name);
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Donors donor = donorservice.getDonorByname(name);
        log.info("Returning donor: {}", donor);
        return ResponseEntity.ok(donor);

	}
	
	
}
