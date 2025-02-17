package com.eureka.Product_service.productservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eureka.Product_service.model.Product;
import com.eureka.Product_service.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private  ProductRepository productRepo;
	
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}
	
	public Product getProductById(Long productId) {
		return productRepo.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));
	}
	
	public Product addProduct(Product product) {
		return productRepo.save(product);
	}
 
	
}