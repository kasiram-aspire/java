package com.example.SpringbootExercise.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringbootExercise.models.Employee;
import com.example.SpringbootExercise.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
   private Logger logger=LoggerFactory.getLogger(EmployeeController.class);
   
   @Autowired
   EmployeeService employeeservice;
   
   @PostMapping("/add")
   public String add(@RequestBody Employee emp )
   {
	   logger.info("Received request to add employee:",emp);   
	   employeeservice.addEmployeeWithAdress(emp);
	   logger.info("Employee added successfully:",emp);
	   return"added";   //add  employee 
   }
   @GetMapping("/get")
   public List<Employee> show()
   {   logger.info("Fetching all employees");
       List<Employee> employees = employeeservice.showemployee();
       logger.info("Total employees retrieved: {}", employees.size());
	   return employeeservice.showemployee();    //get all employee details
	   
   }
   @GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request)
	{  CsrfToken csrftoken= (CsrfToken) request.getAttribute("_csrf");
	   logger.debug("Retrieved CSRF token: {}", csrftoken);  //generate csrf token
		return csrftoken;
	}
   @GetMapping("/session")
  	public String getsessionid(HttpServletRequest request)
  	{
  		return request.getSession().getId();    //return session id
  	}
   @GetMapping("/get/{name}")    
 	public List<Employee> getsessionid(@PathVariable String name)
 	{    logger.info("Fetching employees with name: {}", name);
 		List<Employee> employees = employeeservice.getByName(name);
 		logger.info("Total employees retrieved for name {}: {}", name, employees.size());
 		return employeeservice.getByName(name);       //if the employee name is present it return the particular employee details
 	}
}
