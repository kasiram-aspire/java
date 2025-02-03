package com.example.SpringbootExercise.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringbootExercise.models.Details;
import com.example.SpringbootExercise.models.Employee;
import com.example.SpringbootExercise.repository.DetailsRepository;
import com.example.SpringbootExercise.repository.EmployeeRepository;
@Service
public class EmployeeService {

	@Autowired 
	DetailsRepository detailrepo;
	@Autowired 
	EmployeeRepository emprepo;
	public void addEmployeeWithAdress(Employee emp) {
		// TODO Auto-generated method stub
		     emprepo.save(emp);    
	}
	public List<Employee> showemployee() {
		return emprepo.findAll();
	}
}
