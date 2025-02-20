package com.eureka.Product_service.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	 private Logger log=LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		 log.info("Fetching all products...");
		 List<Product> products = productService.getAllProduct();
	      log.info("Total products fetched: {}", products.size());
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@PostMapping("/addproducts")
	public ResponseEntity<String> addProducts(@RequestBody Product product){
		 log.info("Adding product: {}", product);
		productService.addProduct(product);
		log.info("Product added successfully with ID: {}", product.getId());
		return ResponseEntity.ok("Product addedd successfully \n "+product);
	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Long productId){
		 log.info("Fetching product with ID: {}", productId);
		 Product product = productService.getProductById(productId);
	        if (product != null) {
	            log.info("Product found: {}", product);
	            return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
	        } else {
	            log.warn("Product with ID {} not found", productId);
	            return ResponseEntity.notFound().build();
	        }
		
	}
	
	
}
