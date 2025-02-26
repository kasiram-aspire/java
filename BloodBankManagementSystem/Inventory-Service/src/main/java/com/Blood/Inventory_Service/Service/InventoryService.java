package com.Blood.Inventory_Service.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Blood.Inventory_Service.Exception.DataAlreadyPresent;
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
    
    public Inventory addDonorToTheInventory(String donorname, Long unitOfBlood, LocalDate bloodGivenDate) {
    	Inventory name= inventoryrepo.findByDonorname(donorname);
  	if(name!=null)  	
  	{
    		throw new DataAlreadyPresent("the name{"+donorname+"} already present");
   	}
        Inventory inventory = new Inventory();
        inventory.setDonorname(donorname);
        inventory.setBloodGivenDate(bloodGivenDate);
        inventory.setUnitOfBlood(unitOfBlood);

        // Blocking WebClient call
        Donordto donordto = webClientBuilder.build()
                .get()
                .uri("http://USER-SERVICE/user/donor/getByName/" + donorname)  
                .retrieve()
                .bodyToMono(Donordto.class)
                .block();  //  Blocks until response is received

        if (donordto != null) {
            inventory.setAge(donordto.getAge());
            inventory.setBloodGroup(donordto.getBloodGroup());
        }
         inventoryrepo.save(inventory); // Save after data is set
         BloodCount bloodgroup=bloodcountrepo.findByBloodGroup(donordto.getBloodGroup());
         if(bloodgroup==null)
         {
        	BloodCount bloodcount=new BloodCount();
        	bloodcount.setBloodGroup(donordto.getBloodGroup());
        	bloodcount.setUnits(unitOfBlood);
        	bloodcountrepo.save(bloodcount);
         }
         else
         {
        	  bloodgroup=bloodcountrepo.findByBloodGroup(donordto.getBloodGroup());
         	  Long sum=unitOfBlood+bloodgroup.getUnits();
        	  bloodgroup.setUnits(sum);
        	  bloodcountrepo.save(bloodgroup);
         }
         
         return inventory;
    }

	public List<Inventory> getDonorFromInventroy() {
		  return inventoryrepo.findAll();
		
	}

	public List<BloodCount> getTotalAmountOfBlood() {
		
		return   bloodcountrepo.findAll();
	}

	public BloodCount getCountBasedOnBloodGroup(String bloodgroup) {
		return bloodcountrepo.findByBloodGroup(bloodgroup);
	}
}
