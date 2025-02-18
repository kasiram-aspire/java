package com.eureka.Product_service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.Product_service.model.Product;
import com.eureka.Product_service.productservice.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<>(productService.getAllProduct(),HttpStatus.OK);
	}
	
	@PostMapping("/addproducts")
	public ResponseEntity<String> addProducts(@RequestBody Product product){
		productService.addProduct(product);
		return ResponseEntity.ok("Product addedd successfully \n "+product);
	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Long productId){
		return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
	}
	
	
}
