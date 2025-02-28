package com.blood.Request_Service.Controller;
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
import org.springframework.web.bind.annotation.RestController;
import com.blood.Request_Service.Model.Request;
import com.blood.Request_Service.Model.Status;
import com.blood.Request_Service.Service.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {
	private static final Logger log = LoggerFactory.getLogger(RequestController.class);
  @Autowired
  RequestService requestservice;
  
  @PostMapping("/addBloodRequest")
   public ResponseEntity<Request> addRequestFromHospitol(@RequestHeader("X-User-Role") String role,@RequestBody Request request)
   {
	  log.info("Received request to add blood request from hospital: {}", request);
	  if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
	  Request savedRequest = requestservice.addRequestFromHospitol(request);
      log.info("Blood request added successfully: {}", savedRequest);
      return ResponseEntity.ok(savedRequest);
   }
   @GetMapping("/getAllHospitolRequest")
   public ResponseEntity<List<Request>> getAllHospitolRequest(@RequestHeader("X-User-Role") String role)
   {

       log.info("Received request to fetch all hospital requests.");
	   if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }

       List<Request> requests = requestservice.getAllHospitolRequest();
       log.info("Returning {} hospital requests.", requests.size());
       return ResponseEntity.ok(requests);

   }
   
   @PostMapping("/getRequest/{DATE}")
   public ResponseEntity<List<Request>> getRequestByDate(@RequestHeader("X-User-Role") String role,@PathVariable LocalDate DATE)
   {
	   log.info("Received request to fetch requests for date: {}",DATE);	    
	   if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
	   List<Request> requests = requestservice.getRequestByDate(DATE);
       log.info("Returning {} requests for date: {}", requests.size(),DATE);
       return ResponseEntity.ok(requests);
   }
   @PostMapping("/setStatusrequest/{name}/{status}/{Hospitolname}")  //set the blood status for hospitol request
   public ResponseEntity<Status> setTheRequest(@RequestHeader("X-User-Role") String role,@PathVariable String name,@PathVariable String status,@PathVariable String Hospitolname)
   {

       log.info("Received request to update status for '{}' in hospital '{}' to '{}'", name,Hospitolname, status);
	   if (!"ADMIN".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
       Status updatedStatus = requestservice.setTheRequest(name, status,Hospitolname);
       log.info("Status updated successfully for '{}' in hospital '{}': {}", name,Hospitolname, updatedStatus);
       return ResponseEntity.ok(updatedStatus);

   }
   @GetMapping("/getStatusRequest")  //get all the satus request 
   public ResponseEntity<List<Status>> getTheRequest(@RequestHeader("X-User-Role") String role)
   {

       log.info("Received request to fetch all status requests.");
	   if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }
       List<Status> statuses = requestservice.getRequest();
       log.info("Returning {} status requests.", statuses.size());
       return ResponseEntity.ok(statuses);

   }
   @PostMapping("/RequestNotificationForBlood/{bloodGroup}")  // send email notification to related blood group people
    public ResponseEntity<String> requestNotificationForBlood(@RequestHeader("X-User-Role") String role,@PathVariable String bloodGroup)
    {

       log.info("Received request to send blood notification for blood group '{}'", bloodGroup);
	   if (!"ADMIN".equalsIgnoreCase(role) && !"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	    }


       String notificationStatus = requestservice.requestNotificationForBlood(bloodGroup);
       log.info("Blood request notification sent successfully for blood group '{}'", bloodGroup);
       return ResponseEntity.ok(notificationStatus);
    }
     
}
