package com.blood.Request_Service.Controller;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blood.Request_Service.Model.Request;
import com.blood.Request_Service.Model.Status;
import com.blood.Request_Service.Service.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {
  @Autowired
  RequestService requestservice;
  
  @PostMapping("/addRequest")
   public ResponseEntity<Request> addRequestFromHospitol(@RequestBody Request request)
   {
	  return ResponseEntity.ok(requestservice.addRequestFromHospitol(request));
   }
   @GetMapping("/getAllRequest")
   public ResponseEntity<List<Request>> getAllHospitolRequest()
   {
	   return ResponseEntity.ok(requestservice.getAllHospitolRequest());
   }
   
   @PostMapping("/getRequest/{DATE}")
   public ResponseEntity<List<Request>> getRequestByDate(@PathVariable LocalDate DATE)
   {
	   return ResponseEntity.ok(requestservice.getRequestByDate(DATE));
   }
   @PostMapping("/setrequest/{name}/{status}/{Hospitolname}")
   public ResponseEntity<Status> setTheRequest(@PathVariable String name,@PathVariable String status,@PathVariable String Hospitolname)
   {
	   return ResponseEntity.ok(requestservice.setTheRequest(name,status,Hospitolname));
   }
   @GetMapping("/getRequest")
   public ResponseEntity<List<Status>> getTheRequest()
   {
	   return ResponseEntity.ok(requestservice.getRequest());
   }
   @PostMapping("/RequestForBlood/{bloodGroup}")
    public ResponseEntity<String> requestNotificationForBlood(@PathVariable String bloodGroup)
    {
	   return ResponseEntity.ok(requestservice.requestNotificationForBlood(bloodGroup));
    }
     
}
