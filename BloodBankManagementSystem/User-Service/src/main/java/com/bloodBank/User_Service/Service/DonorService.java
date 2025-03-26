package com.bloodBank.User_Service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bloodBank.User_Service.Exceptions.DataAlreadyPresent;
import com.bloodBank.User_Service.Exceptions.DonorNotFoundException;
import com.bloodBank.User_Service.Exceptions.IDNotFoundException;
import com.bloodBank.User_Service.Model.Donors;
import com.bloodBank.User_Service.Repo.DonorsRepository;
import com.bloodBank.User_Service.ServiceImplementation.DonorServiceImplementation;

@Service
public class DonorService implements  DonorServiceImplementation {
	@Autowired  
	DonorsRepository donorrepo;
	@Autowired
	  private  WebClient.Builder webClientBuilder;
	public Donors addDonors(Donors donor) {
		 Donors donorName=donorrepo.findByDonorName(donor.getDonorName());
		if(donorName==null)
		{
			donorrepo.save(donor);
			String emailServiceUrl = "http://localhost:8083/api/email/send"; // Replace with actual URL of email service
             String message="Your profile is added to the blood bank db successfully";
             String subject="welcome to blood bank";
             WebClient webClient = WebClient.builder()
            	        .baseUrl("http://localhost:8083") //Set base URL
            	        .build();

            	String response = webClient.post()
            	        .uri(uriBuilder -> uriBuilder
            	                .path("/api/email/send") // Use only the relative path
            	                .queryParam("to", donor.getEmailId())
            	                .queryParam("subject", subject)
            	                .queryParam("message", message)
            	                .build())
            	        .retrieve()
            	        .bodyToMono(String.class)
            	        .block(); // Blocking for simplicity

            	System.out.println(response);
		}
		else
		{
			throw new DataAlreadyPresent("The donor:"+donor.getDonorName()+"is already registered in db");
		}
		return donor;
	}

	public List<Donors> getDonors() {
		
		return donorrepo.findAll();
	}

	public Donors getById(Long id) {
		Donors donor=donorrepo.findById(id).orElse(null);
		if(donor==null)
		{
		    throw new IDNotFoundException("The id:"+id+"not found in db");
		}
		else
		{
		  return donorrepo.findById(id).get();
		}
	}

	public Donors update(Donors donor) {

		Donors existingDonor=donorrepo.findById(donor.getId()).orElse(null);
		if(existingDonor==null)
		{
		    throw new IDNotFoundException("The id:"+donor.getId()+" not found in db");
		}
		existingDonor.setAge(donor.getAge());
		existingDonor.setBloodGroup(donor.getBloodGroup());
		existingDonor.setDateOfBirth(donor.getDateOfBirth());
		existingDonor.setDonorName(donor.getDonorName());
		existingDonor.setEmailId(donor.getEmailId());
		existingDonor.setPhoneno(donor.getPhoneno());
		return donorrepo.save(existingDonor);
	}

	public List<Donors> getDonorByBloodGroupName(String bloodgroup) {
		    List<Donors> donor=new ArrayList<>();
		    donor=donorrepo.findByBloodGroup(bloodgroup);
		    if(donor.isEmpty())
		    {
		    	 throw new DonorNotFoundException("The particular:"+bloodgroup+" not found");
		    }
		    else
		    {
		    	return donor;
		    }
		
	}

	public List<Donors> getDonorByBloodGroupNameAndAge(String bloodgroup, Integer age) {
		List<Donors> donors = donorrepo.findByBloodGroup(bloodgroup);
	    
	    if (donors.isEmpty()) {
	        throw new DonorNotFoundException("The particular blood group: " + bloodgroup + " not found");
	    }

	    return donors.stream()
	                 .filter(donor -> donor.getAge() >= age)
	                 .collect(Collectors.toList());
	}

	public Donors getDonorByname(String name) {
		System.out.println(name);
		 Donors donorName=donorrepo.findByDonorName(name);
		return donorName;
	}

}
