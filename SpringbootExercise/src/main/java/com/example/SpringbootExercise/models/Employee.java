package com.example.SpringbootExercise.models;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee") 
public class Employee {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) 
   private int id;
   private String employeeName;
   private String email;
   @OneToMany(mappedBy ="employee",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
   @JsonManagedReference 
   List<Details>detailslist;
   
	public Employee() {
	}
	
	public Employee(int id, String employeeName, String email) {
		this.id = id;
		this.employeeName = employeeName;
		this.email =email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Details> getDetailslist() {
		return detailslist;
	}

	public void setDetailslist(List<Details> detailslist) {
		this.detailslist = detailslist;
	}
   
   
}
