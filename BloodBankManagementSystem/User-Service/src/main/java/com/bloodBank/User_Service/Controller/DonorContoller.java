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
import com.bloodBank.User_Service.Service.DonorService;

@RestController
@RequestMapping("/user/donor")
public class DonorContoller {
	@Autowired 
	DonorService donorservice;
    
	@PostMapping("/addDonor")
	public ResponseEntity<Donors> addDonor(@RequestBody Donors donor)
	{
		   return ResponseEntity.ok(donorservice.addDonors(donor));
	}
	@GetMapping("/getDonor")
	public ResponseEntity<List<Donors>> getDonors()
	{
		 return ResponseEntity.ok(donorservice.getDonors());
	}
	@GetMapping("/getDonorbyId/{id}")
	public ResponseEntity<Donors> getDonorById(@PathVariable Long id)
	{
		 return ResponseEntity.ok(donorservice.getById(id));
	}
	@PostMapping("/update")
	public ResponseEntity<Donors> updateDonor(@RequestBody Donors donor)
	{
		return ResponseEntity.ok(donorservice.update(donor));
	}
	@PostMapping("/getByBloodGrouName/{Bloodgroup}")
	public ResponseEntity<List<Donors>> getDonorByBloodGroupName(@PathVariable String Bloodgroup)
	{
		return ResponseEntity.ok(donorservice.getDonorByBloodGroupName(Bloodgroup));
	}
	@PostMapping("/getByBloodGrouNameAndAge/{Bloodgroup}/{Age}")
	public ResponseEntity<List<Donors>> getDonorByBloodGroupNameAndAge(@PathVariable String Bloodgroup,@PathVariable Integer Age)
	{
		return ResponseEntity.ok(donorservice.getDonorByBloodGroupNameAndAge(Bloodgroup,Age));
	}
	@GetMapping("/getByName/{name}")
	public ResponseEntity<Donors> getDonorByBloodGroupNameAndAge(@PathVariable String name)
	{
		return ResponseEntity.ok(donorservice.getDonorByname(name));
	}
	
}
