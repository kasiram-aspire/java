package com.example.SpringbootExercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringbootExercise.models.Product;
import com.example.SpringbootExercise.services.ProductService;

@RestController
public class productController {
	@Autowired
	public ProductService productservice;
	@RequestMapping("/login")
    public List<Product> show()
    {   productservice.addProduct();
    	return productservice.getProduct();
    }
}
