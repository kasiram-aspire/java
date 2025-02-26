package com.Blood.Inventory_Service.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Blood.Inventory_Service.Model.BloodCount;
import com.Blood.Inventory_Service.Model.Inventory;
import com.Blood.Inventory_Service.Service.InventoryService;
import com.Blood.Inventory_Service.dto.Donordto;

@RestController
@RequestMapping("/inventroy")
public class InventoryController {
	@Autowired 
	Donordto donordto;
	@Autowired
	InventoryService inventoryservice;
	@PostMapping("/addDonorInventory")
	public ResponseEntity<Inventory> addDonartoTheInventory(@RequestParam String donorname,@RequestParam Long UnitOfBlood,@RequestParam LocalDate BloodGivenDate)
	{		
		return ResponseEntity.ok(inventoryservice.addDonorToTheInventory(donorname,UnitOfBlood,BloodGivenDate));
	}
	@GetMapping("/getDonor")
	public ResponseEntity<List<Inventory>> getDonarfromTheInventory()
	{		
		return ResponseEntity.ok(inventoryservice.getDonorFromInventroy());
	}
	@GetMapping("/getBloodCount")
	public ResponseEntity<List<BloodCount>> getTotalAmountOfBlood()
	{
		return ResponseEntity.ok(inventoryservice.getTotalAmountOfBlood());
	}
	@GetMapping("/getBloodCount/{bloodgroup}")
	public ResponseEntity<BloodCount> getCountBasedOnBloodGroup(@PathVariable String bloodgroup)
	{
		return ResponseEntity.ok(inventoryservice.getCountBasedOnBloodGroup(bloodgroup));
	}
	
}
