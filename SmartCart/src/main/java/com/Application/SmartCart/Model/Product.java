package com.Application.SmartCart.Model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
@Entity
public class Product {
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String productName;
	    @ManyToMany(mappedBy="products",fetch=FetchType.LAZY)
	    private Set<User> users = new HashSet<>();
	    
		public Product() {
		}
	    public Product(String productName) {
	        this.productName =productName;
	    }

		public Product(Long id, String productName, Set<User> users) {
			this.id = id;
			this.productName = productName;
			this.users = users;
		}

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public Set<User> getUsers() {
			return users;
		}
		public void setUsers(Set<User> users) {
			this.users = users;
		}
		
	
}
