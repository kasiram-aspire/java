package com.blood.Request_Service.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.blood.Request_Service.Exception.IDNotFoundException;
import com.blood.Request_Service.Model.Request;
import com.blood.Request_Service.Model.Status;
import com.blood.Request_Service.Repository.RequestRepository;
import com.blood.Request_Service.Repository.StatusRepository;
import com.blood.Request_Service.dto.Donordto;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestrepo;
    @Autowired
    StatusRepository statusrepo;
    @Autowired
    private WebClient.Builder webClientBuilder;
	public Request addRequestFromHospitol(Request request) {
		
		 return requestrepo.save(request);
		 
	}
	public List<Request> getAllHospitolRequest() {
		return requestrepo.findAll();
	}
	public List<Request> getRequestByDate(LocalDate DATE) {
		List<Request> request=requestrepo.findByDatelimit(DATE);
		if(request.isEmpty())
		{
			throw new IDNotFoundException("In this Date: "+DATE+" no data is found");
		}
		    return request;
	}
	public Status setTheRequest(String name, String status, String hospitolname) {
		  Status statusobj=new Status();
		  try
		  {
			  System.out.println(name);
			  Donordto donordto = webClientBuilder.build()
		                .get()
		                .uri("http://USER-SERVICE/user/donor/getByName/" + name)
		                .header("X-User-Role","ADMIN")
		                .retrieve()
		                .bodyToMono(Donordto.class)
		                .block();  
		  statusobj.setAge(donordto.getAge());
		  statusobj.setDonorName(name);
		  statusobj.setBloodGroup(donordto.getBloodGroup());
		  statusobj.setHospitolname(hospitolname);
		  statusobj.setStatus(status);
		  statusrepo.save(statusobj);
		  if(status.equals("Accepted"))
		  {
		  String statusOfDeletedItems = webClientBuilder.build()
	                .post()
	                .uri("http://INVENTORY-SERVICE/inventroy/deleteUserFromInventory/"+name)
	                .header("X-User-Role","ADMIN")
	                .retrieve()
	                .bodyToMono(String.class)
	                .block();
		  System.out.println(statusOfDeletedItems);
		  }
		  return statusobj;
		  }
		  
		  catch(Exception e)
		  {
			  throw new IDNotFoundException("Donor not found");
		  }
		 
	}
	public List<Status> getRequest() {
		
		return statusrepo.findAll();
	}
	public String requestNotificationForBlood(String bloodGroup) {
		List<Donordto> donordtoList = webClientBuilder.build()
			    .post()
			    .uri("http://USER-SERVICE/user/donor/getByBloodGrouName/" + bloodGroup)
			    .header("X-User-Role","ADMIN")
			    .retrieve()
			    .bodyToMono(new ParameterizedTypeReference<List<Donordto>>() {})
			    .block();
		String emailServiceUrl = "http://localhost:8083/api/email/send"; // Replace with actual URL of email service
        String message="urgent need of blood: "+bloodGroup;
        String subject="welcome to blood bank";
        WebClient webClient = WebClient.builder()
       	        .baseUrl("http://localhost:8083") //Set base URL
       	        .build();
		for(Donordto donor:donordtoList)
		{
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
        	     System.out.println(response);//
        	     return response;
		}
		return "no donors found";

		
	}
	
	

}
