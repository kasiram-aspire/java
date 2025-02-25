package com.bloodBank.User_Service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloodBank.User_Service.Model.Donors;
import com.bloodBank.User_Service.Model.Hospitol;
import com.bloodBank.User_Service.Service.DonorService;
import com.bloodBank.User_Service.Service.HospitolService;

@RestController
@RequestMapping("/user/hospitol")
public class HospitoController {
	@Autowired 
	 HospitolService hospitolservice;
    
	@PostMapping("/addHospitol")
	public ResponseEntity<Hospitol> addhospitol(@RequestBody Hospitol hospitol)
	{
		   return ResponseEntity.ok( hospitolservice.addHospitol(hospitol));
	}
	@GetMapping("/getHospitol")
	public ResponseEntity<List<Hospitol>> getHospitoldetails()
	{
		 return ResponseEntity.ok(hospitolservice.getHospitolDetails());
	}
	@GetMapping("/getHospitolbyId/{id}")
	public ResponseEntity<Hospitol>getHospitolById(@PathVariable Long id)
	{
		 return ResponseEntity.ok(hospitolservice.getById(id));
	}
	@PostMapping("/update")
	public ResponseEntity<Hospitol> updateHospitol(@RequestBody Hospitol hospitol)
	{
		 return ResponseEntity.ok( hospitolservice.updateHospitol(hospitol));
	}
}
