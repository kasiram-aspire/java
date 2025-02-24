package com.eureka.Order_service.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.eureka.Order_service.Model.Order;
import com.eureka.Order_service.Repository.OrderRepository;
import com.eureka.Order_service.dto.ProductDto;
import com.eureka.Order_service.dto.orderResponseDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderRepository orderrepo;
	@Autowired
	  private  WebClient.Builder webClientBuilder;
	// create a method to place order
	@PostMapping("/placeorder")
	public Mono<ResponseEntity<orderResponseDTO>> placeOrder(@RequestHeader("X-User-Role") String role,@RequestBody Order order)
	{
		log.info("Received order request: {}", order);
		// fetch product details from productservice
		if (!"USER".equalsIgnoreCase(role)) {
	        log.warn("Unauthorized access attempt by role: {}", role);
	        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(null));
	    }
		return webClientBuilder.build().get().uri("http://PRODUCT-SERVICE/product/products/"+ order.getProductId()).retrieve()
				.bodyToMono(ProductDto.class).map(productDTO->{
					log.info("Fetched product details: {}", productDTO);
					 orderResponseDTO responseDTO=new orderResponseDTO();
					 responseDTO.setProductId(order.getProductId());
					 responseDTO.setQuantity(order.getQuantity());
					 // fetch from product
					 responseDTO.setProductName(productDTO.getName());
					 responseDTO.setProductPrice(productDTO.getPrice());
					 responseDTO.setTotalPrice(order.getQuantity()*productDTO.getPrice());
					 
					 orderrepo.save(order);
					 responseDTO.setOrderId(order.getId());
					 log.info("Order placed successfully: {}", responseDTO);
					 return ResponseEntity.ok(responseDTO);
					 
				}
						);
	}
	@GetMapping("/getorder")
	public List<Order> getallorders(@RequestHeader("X-User-Role") String role)
	{
		
		log.info("Fetching all orders...");
		log.info(" access attempt by role: {}", role);
        // Role-based authorization check
        if (!"USER".equalsIgnoreCase(role) || !"ADMIN".equalsIgnoreCase(role)) {
            log.warn("Unauthorized access attempt by role: {}", role);
            System.out.println("Access Denied");
        }

        List<Order> orders = orderrepo.findAll();
        log.info("Total orders found: {}", orders.size());

        return orders;
    }
		
	}
	
