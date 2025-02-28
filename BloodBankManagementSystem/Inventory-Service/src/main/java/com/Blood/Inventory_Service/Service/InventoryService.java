package com.Blood.Inventory_Service.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Blood.Inventory_Service.Exception.DataAlreadyPresent;
import com.Blood.Inventory_Service.Exception.IDNotFoundException;
import com.Blood.Inventory_Service.Model.BloodCount;
import com.Blood.Inventory_Service.Model.Inventory;
import com.Blood.Inventory_Service.Repository.BloodCountRepository;
import com.Blood.Inventory_Service.Repository.InventroyRepo;
import com.Blood.Inventory_Service.dto.Donordto;

@Service
public class InventoryService {
    @Autowired
    private InventroyRepo inventoryrepo;
    @Autowired
    private BloodCountRepository bloodcountrepo;
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    // add the donor to inventory
    public Inventory addDonorToTheInventory(String donorname, Long unitOfBlood, LocalDate bloodGivenDate) {
    	Inventory name= inventoryrepo.findByDonorname(donorname); // get donor obj from inventory by name 
  	if(name!=null)   //if donor not present throw error	
  	{
    		throw new DataAlreadyPresent("the name{"+donorname+"} already present");
   	}
  	    // set the value to the inventory
        Inventory inventory = new Inventory();
        inventory.setDonorname(donorname);
        inventory.setBloodGivenDate(bloodGivenDate);
        inventory.setUnitOfBlood(unitOfBlood);

        // call the user service to get donor by name for remaining valuse to set
        Donordto donordto = webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/user/donor/getByName/" + donorname)
                .header("X-User-Role","ADMIN")
                .retrieve()
                .bodyToMono(Donordto.class)
                .block();  //  Blocks until response is received

        if (donordto != null) {
            inventory.setAge(donordto.getAge());
            inventory.setBloodGroup(donordto.getBloodGroup());
        }
         inventoryrepo.save(inventory); // Save after data is set
         BloodCount bloodgroup=bloodcountrepo.findByBloodGroup(donordto.getBloodGroup());
         // create the new blood group into bloodcount db
         if(bloodgroup==null)
         {
        	BloodCount bloodcount=new BloodCount();
        	bloodcount.setBloodGroup(donordto.getBloodGroup());
        	bloodcount.setUnits(unitOfBlood);
        	bloodcountrepo.save(bloodcount);
         }
         // if the blood group already present it use the same object
         else
         {
        	  bloodgroup=bloodcountrepo.findByBloodGroup(donordto.getBloodGroup());
         	  Long sum=unitOfBlood+bloodgroup.getUnits();  //whenever new user added that time blood count also added
        	  bloodgroup.setUnits(sum);
        	  bloodcountrepo.save(bloodgroup);
         }
         
         return inventory;
    }
     //get the donor info from db
	public List<Inventory> getDonorFromInventroy() {
		  return inventoryrepo.findAll();
		
	}
     //get the total blood present
	public List<BloodCount> getTotalAmountOfBlood() {
		
		return   bloodcountrepo.findAll();
	}
    //get the particular blood group count
	public BloodCount getCountBasedOnBloodGroup(String bloodgroup) {
		return bloodcountrepo.findByBloodGroup(bloodgroup);
	}
      // delete the user from the inventory
	public boolean deleteUserFromInventory(String donorname) {
		Inventory inventory=inventoryrepo.findByDonorname(donorname);
		if (inventory != null) {
			String bloodgroup=inventory.getBloodGroup();
			 BloodCount bloodgroupname=bloodcountrepo.findByBloodGroup(bloodgroup);
			 Long bloodunit=bloodgroupname.getUnits()-inventory.getUnitOfBlood(); // at the time of removing donor,blood count also decrese
			 bloodgroupname.setUnits(bloodunit);
			 bloodcountrepo.save(bloodgroupname);
			inventoryrepo.delete(inventory);
            return true;
        }
		return false;
	}
        // update the donor who already present
	public Inventory updateInventroy(Inventory inventory) {
		Inventory existingInventory=inventoryrepo.findByDonorname(inventory.getDonorname());	
		if(existingInventory==null) //check if the donor is present
		{
			throw new IDNotFoundException("The id:"+inventory.getId()+" not found in db");
		}
		Long prviouscount=existingInventory.getUnitOfBlood(); //get the previous count for updating the blood count
		existingInventory.setAge(inventory.getAge());
		existingInventory.setBloodGivenDate(inventory.getBloodGivenDate());
		existingInventory.setBloodGroup(inventory.getBloodGroup());
		existingInventory.setDonorname(inventory.getDonorname());
		existingInventory.setUnitOfBlood(inventory.getUnitOfBlood());
//		System.out.println(prviouscount);
		inventoryrepo.save(existingInventory);
		BloodCount bloodgroup=bloodcountrepo.findByBloodGroup(inventory.getBloodGroup());
		if (bloodgroup != null) {
	        // Calculate new blood unit count
	        Long unitDifference = inventory.getUnitOfBlood() - prviouscount; //get the unit difference from present count and previous count
//	        System.out.println(unitDifference);
	        bloodgroup.setUnits(bloodgroup.getUnits() + unitDifference); //then update the difference adding with already present in database

	        // Save updated blood count
	        bloodcountrepo.save(bloodgroup);
	    }
   	    return existingInventory;
		 
	}
}
