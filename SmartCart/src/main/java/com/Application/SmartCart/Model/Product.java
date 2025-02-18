package com.Application.SmartCart.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
@Entity
public class Product {
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String productName;
	    @OneToOne(mappedBy="product",fetch=FetchType.LAZY)
	    @JsonBackReference
	    private User user;
		public Product() {
		}
		public Product(Long id, String productName, User user) {
			this.id = id;
			this.productName = productName;
			this.user = user;
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
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
	    
	
}
