package com.example.SpringbootExercise.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Grocessry_product")
public class GrocessryProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String productName;
	@OneToMany(mappedBy = "grocessryproduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	  private List<User> user=new ArrayList<>(); 
	
	public GrocessryProduct()
	{
		
	}
	public GrocessryProduct(long id, String productName) {
		Id = id;
		this.productName = productName;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
}
