package com.example.SpringbootExercise.controller;

import java.util.List;

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
   @Autowired
   EmployeeService employeeservice;
   
   @PostMapping("/add")
   public String add(@RequestBody Employee emp )
   {
	   employeeservice.addEmployeeWithAdress(emp);
	   return"added";
   }
   @GetMapping("/get")
   public List<Employee> show()
   {
	   return employeeservice.showemployee();
   }
   @GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request)
	{
		return (CsrfToken) request.getAttribute("_csrf");
	}
   @GetMapping("/session")
  	public String getsessionid(HttpServletRequest request)
  	{
  		return request.getSession().getId();
  	}
   @GetMapping("/get/{name}")
 	public List<Employee> getsessionid(@PathVariable String name)
 	{
 		return employeeservice.getByName(name);
 	}
}
