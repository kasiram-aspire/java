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
import com.bloodBank.User_Service.Model.Hospitol;
import com.bloodBank.User_Service.Service.DonorService;
import com.bloodBank.User_Service.Service.HospitolService;

@RestController
@RequestMapping("/user/hospitol")
public class HospitoController {
	private static final Logger log = LoggerFactory.getLogger(HospitoController .class);
	@Autowired 
	 HospitolService hospitolservice;
    
	@PostMapping("/addHospitol")
	public ResponseEntity<Hospitol> addhospitol(@RequestHeader("X-User-Role") String role,@RequestBody Hospitol hospitol)
	{

        log.info("Received request to add hospital: {}", hospitol);
		if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Hospitol savedHospitol = hospitolservice.addHospitol(hospitol);
        log.info("Hospital added successfully: {}", savedHospitol);
        return ResponseEntity.ok(savedHospitol);
	}
	@GetMapping("/getHospitol")
	public ResponseEntity<List<Hospitol>> getHospitoldetails(@RequestHeader("X-User-Role") String role)
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
	@GetMapping("/getHospitolbyId/{id}")
	public ResponseEntity<Hospitol>getHospitolById(@RequestHeader("X-User-Role") String role,@PathVariable Long id)
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
	public ResponseEntity<Hospitol> updateHospitol(@RequestHeader("X-User-Role") String role,@RequestBody Hospitol hospitol)
	{
        log.info("Received request to update hospital: {}", hospitol);
		if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Hospitol updatedHospitol = hospitolservice.updateHospitol(hospitol);
        log.info("Hospital updated successfully: {}", updatedHospitol);
        return ResponseEntity.ok(updatedHospitol);

	}
	@PostMapping("/getHospitolbyName/{hospitolname}")
	public ResponseEntity<Hospitol> updateHospitol(@RequestHeader("X-User-Role") String role,@PathVariable String hospitolname)
	{
		log.info("Received request to fetch hospital by name: {}", hospitolname);
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Hospitol hospitol = hospitolservice.getHospitolByName(hospitolname);
        log.info("Returning hospital: {}", hospitol);
        return ResponseEntity.ok(hospitol);

	}
}
