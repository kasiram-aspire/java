package com.example.SpringbootExercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringbootExercise.models.Product;
import com.example.SpringbootExercise.services.ProductService;

@RestController
public class productController {
	@Autowired
	public ProductService productservice;
	@GetMapping("/product")
    public List<Product> show()
    {   productservice.addProduct();
    	return productservice.getProduct();
    }
	@GetMapping("/product/{productId}")
	public Product getById(@PathVariable int productId)
	{    productservice.addProduct();
		return productservice.getElementById(productId);
	}
}

