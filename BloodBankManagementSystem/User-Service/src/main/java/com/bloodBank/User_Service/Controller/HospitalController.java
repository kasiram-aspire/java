package com.bloodBank.User_Service.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloodBank.User_Service.Exceptions.DataAlreadyPresent;
import com.bloodBank.User_Service.Exceptions.IDNotFoundException;
import com.bloodBank.User_Service.Model.Hospitol;
import com.bloodBank.User_Service.Service.HospitolService;

@RestController
@RequestMapping("/user/hospital")
public class HospitalController {
	private static final Logger log = LoggerFactory.getLogger(HospitalController .class);
	@Autowired 
	 HospitolService hospitolservice;
	
	@ExceptionHandler(DataAlreadyPresent.class)
	public ResponseEntity<String> handleDataAlreadyPresentException(DataAlreadyPresent ex) {
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	 @ExceptionHandler(IDNotFoundException.class)
	    public ResponseEntity<String> handleIDNotFoundException(Exception ex) {
	    	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	
	@PostMapping("/addHospital")
	public ResponseEntity<Hospitol> addhospital(@RequestHeader("X-User-Role") String role,@RequestBody Hospitol hospital)
	{

        log.info("Received request to add hospital: {}", hospital);
		if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Hospitol savedHospitol = hospitolservice.addHospitol(hospital);
        log.info("Hospital added successfully: {}", savedHospitol);
        return ResponseEntity.ok(savedHospitol);
	}
	@GetMapping("/getHospital")
	public ResponseEntity<List<Hospitol>> getHospitaldetails(@RequestHeader("X-User-Role") String role)
	{

        log.info("Received request to fetch all hospitals.");
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
	    List<Hospitol> hospitols = hospitolservice.getHospitolDetails();
	        log.info("Returning {} hospitals", hospitols.size());
	        return ResponseEntity.ok(hospitols);
	}
	@GetMapping("/getHospitalbyId/{id}")
	public ResponseEntity<Hospitol>getHospitalById(@RequestHeader("X-User-Role") String role,@PathVariable Long id)
	{

        log.info("Received request to fetch hospital with ID: {}", id);
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }

        Hospitol hospitol = hospitolservice.getById(id);
        log.info("Returning hospital: {}", hospitol);
        return ResponseEntity.ok(hospitol);

	}
	@PostMapping("/update")
	public ResponseEntity<Hospitol> updateHospital(@RequestHeader("X-User-Role") String role,@RequestBody Hospitol hospital)
	{
        log.info("Received request to update hospital: {}", hospital);
		if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Hospitol updatedHospitol = hospitolservice.updateHospitol(hospital);
        log.info("Hospital updated successfully: {}", updatedHospitol);
        return ResponseEntity.ok(updatedHospitol);

	}
	@PostMapping("/getHospitalbyName/{hospitalname}")
	public ResponseEntity<Hospitol> updateHospitalByName(@RequestHeader("X-User-Role") String role,@PathVariable String hospitalname)
	{
		log.info("Received request to fetch hospital by name:{}", hospitalname);
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Hospitol hospitol = hospitolservice.getHospitolByName(hospitalname);
        log.info("Returning hospital: {}", hospitol);
        return ResponseEntity.ok(hospitol);

	}
}
