package com.example.SpringbootExercise.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "details") 
public class Details {
	 @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY) 
	   private int id;
	   private String adress;
	   @ManyToOne(fetch = FetchType.LAZY) 
	    @JoinColumn(name = "emp_id")  // Foreign key in the database 
	    @JsonBackReference 
	    Employee employee;
	
	public Details() {
	}

	public Details(int id, String adress, Employee employee) {
		this.id = id;
		this.adress = adress;
		this.employee = employee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	   
}
