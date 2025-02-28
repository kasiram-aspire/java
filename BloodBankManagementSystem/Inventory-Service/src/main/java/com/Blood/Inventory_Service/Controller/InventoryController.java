package com.Blood.Inventory_Service.Controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Blood.Inventory_Service.Model.BloodCount;
import com.Blood.Inventory_Service.Model.Inventory;
import com.Blood.Inventory_Service.Service.InventoryService;
import com.Blood.Inventory_Service.dto.Donordto;

@RestController
@RequestMapping("/inventroy")
public class InventoryController {
	private static final Logger log = LoggerFactory.getLogger(InventoryController.class);
	@Autowired 
	Donordto donordto;
	@Autowired
	InventoryService inventoryservice;
	//add the donor to the inventory
	@PostMapping("/addDonorInventory")
	public ResponseEntity<Inventory> addDonartoTheInventory(@RequestHeader("X-User-Role") String role,@RequestParam String donorname,@RequestParam Long UnitOfBlood,@RequestParam LocalDate BloodGivenDate)
	{	
        log.info("Received request to add donor '{}' with {} units of blood on {}", donorname, UnitOfBlood, BloodGivenDate);
		if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Inventory inventory = inventoryservice.addDonorToTheInventory(donorname, UnitOfBlood, BloodGivenDate);
        log.info("Donor '{}' added successfully to inventory.", donorname);
        return ResponseEntity.ok(inventory);

	}
	// get all the donors in the data base
	@GetMapping("/getDonor")
	public ResponseEntity<List<Inventory>> getDonarfromTheInventory(@RequestHeader("X-User-Role") String role)
	{	

        log.info("Received request to fetch all donors from inventory.");
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        List<Inventory> donors = inventoryservice.getDonorFromInventroy();
        log.info("Returning {} donors from inventory.", donors.size());
        return ResponseEntity.ok(donors);
	}
	// get the total counts of each blood group
	@GetMapping("/getBloodCount")
	public ResponseEntity<List<BloodCount>> getTotalAmountOfBlood(@RequestHeader("X-User-Role") String role)
	{
        log.info("Received request to fetch total blood count.");
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }

        List<BloodCount> bloodCounts = inventoryservice.getTotalAmountOfBlood();
        log.info("Returning blood count details.");
        return ResponseEntity.ok(bloodCounts);

	}
	// get the particular count of the blood group
	@GetMapping("/getBloodCount/{bloodgroup}")
	public ResponseEntity<BloodCount> getCountBasedOnBloodGroup(@RequestHeader("X-User-Role") String role,@PathVariable String bloodgroup)
	{
		log.info("Received request to fetch blood count for group '{}'", bloodgroup);
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
		BloodCount count = inventoryservice.getCountBasedOnBloodGroup(bloodgroup);
        log.info("Returning blood count for group '{}': {}", bloodgroup, count);
        return ResponseEntity.ok(count);
	}
	// delete the particular user from the database
	@PostMapping("/deleteUserFromInventory/{donorname}")
	public ResponseEntity<String> deleteUserFromInventory(@RequestHeader("X-User-Role") String role,@PathVariable String donorname)
	{
		log.info("Received request to delete donor '{}' from inventory.", donorname);
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        boolean deleted = inventoryservice.deleteUserFromInventory(donorname);
        if (deleted) {
            log.info("Successfully deleted donor '{}' from inventory.", donorname);
            return ResponseEntity.ok("Inventory with name '" + donorname + "' deleted successfully.");
        } else {
            log.warn("Attempt to delete non-existent donor '{}' from inventory.", donorname);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory with name '" + donorname + "' not found.");
        }

	}
	 // update the user in the inventory database
	@PostMapping("/updateInventroy")
	public ResponseEntity<Inventory> updateInventroy(@RequestHeader("X-User-Role") String role,@RequestBody Inventory inventory)
	{

        log.info("Received request to update inventory record: {}", inventory);
		if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
        Inventory updatedInventory = inventoryservice.updateInventroy(inventory);
        log.info("Inventory record updated successfully: {}", updatedInventory);
        return ResponseEntity.ok(updatedInventory);

	}
	
}
