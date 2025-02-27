package com.Blood.Inventory_Service.Controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	//add the donor to the inventory
	@PostMapping("/addDonorInventory")
	public ResponseEntity<Inventory> addDonartoTheInventory(@RequestParam String donorname,@RequestParam Long UnitOfBlood,@RequestParam LocalDate BloodGivenDate)
	{		
		return ResponseEntity.ok(inventoryservice.addDonorToTheInventory(donorname,UnitOfBlood,BloodGivenDate));
	}
	// get all the donors in the data base
	@GetMapping("/getDonor")
	public ResponseEntity<List<Inventory>> getDonarfromTheInventory()
	{		
		return ResponseEntity.ok(inventoryservice.getDonorFromInventroy());
	}
	// get the total counts of each blood group
	@GetMapping("/getBloodCount")
	public ResponseEntity<List<BloodCount>> getTotalAmountOfBlood()
	{
		return ResponseEntity.ok(inventoryservice.getTotalAmountOfBlood());
	}
	// get the particular count of the blood group
	@GetMapping("/getBloodCount/{bloodgroup}")
	public ResponseEntity<BloodCount> getCountBasedOnBloodGroup(@PathVariable String bloodgroup)
	{
		return ResponseEntity.ok(inventoryservice.getCountBasedOnBloodGroup(bloodgroup));
	}
	// delete the particular user from the database
	@PostMapping("/deleteUserFromInventory/{donorname}")
	public ResponseEntity<String> deleteUserFromInventory(@PathVariable String donorname)
	{
		boolean deleted =inventoryservice.deleteUserFromInventory(donorname);
		 if (deleted) {
	            return ResponseEntity.ok("Inventory with name '" + donorname + "' deleted successfully.");
	        } else {
	        	
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory with name '" + donorname + "' not found.");
	        }
	}
	 // update the user in the inventory database
	@PostMapping("/updateInventroy")
	public ResponseEntity<Inventory> updateInventroy(@RequestBody Inventory inventory)
	{
		return  ResponseEntity.ok(inventoryservice.updateInventroy(inventory));
	}
	
}
