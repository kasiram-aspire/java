package com.example.SpringbootExercise.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringbootExercise.models.Product;
import com.example.SpringbootExercise.services.ProductService;

@RestController
public class productController {
	@Autowired
	public ProductService productservice;
	@GetMapping("/product")       //it show all products in the web
    public List<Product> show()
    {   productservice.addProduct();
    	return productservice.getProduct();
    }
	@GetMapping("/product/{productId}")        //based on the productid it display the product
	public Product getById(@PathVariable int productId)
	{   
		return productservice.getElementById(productId);
	}
	@PostMapping("/add")      // add product using json
	public void addProductElement(@RequestBody Product product)
	{   System.out.println(product);
		productservice.addProductElement(product);
	}
	@PutMapping("/product/updateproduct")    //update the product
	public void Updateproduct(@RequestBody Product product)
	{
		productservice.updateproduct(product);
	}
	@DeleteMapping("/product/deleteproduct/{productId}")  // delete the product
	public void deleteproduct(@PathVariable int productId)
	{
		productservice.deleteproduct(productId);
	}
	
}

