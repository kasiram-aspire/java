package com.Application.SmartCart.Model;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class Customer {
     
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long  phoneNo;
	private String email;
	private String address;
	private Date  dateofbying;
//	List<Product>product;
	
}
