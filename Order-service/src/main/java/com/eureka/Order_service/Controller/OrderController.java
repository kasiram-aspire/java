package com.eureka.Order_service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@Autowired
	private OrderRepository orderrepo;
	@Autowired
	  private  WebClient.Builder webClientBuilder;
	// create a method to place order
	@PostMapping("/placeorder")
	public Mono<ResponseEntity<orderResponseDTO>> placeOrder(@RequestBody Order order)
	{
		// fetch product details from productservice
		return webClientBuilder.build().get().uri("http://PRODUCT-SERVICE/product/products/"+ order.getProductId()).retrieve()
				.bodyToMono(ProductDto.class).map(productDTO->{
					 orderResponseDTO responseDTO=new orderResponseDTO();
					 responseDTO.setProductId(order.getProductId());
					 responseDTO.setQuantity(order.getQuantity());
					 // fetch from product
					 responseDTO.setProductName(productDTO.getName());
					 responseDTO.setProductPrice(productDTO.getPrice());
					 responseDTO.setTotalPrice(order.getQuantity()*productDTO.getPrice());
					 
					 orderrepo.save(order);
					 responseDTO.setOrderId(order.getId());
					 return ResponseEntity.ok(responseDTO);
					 
				}
						);
	}
	@GetMapping("/getorder")
	public List<Order> getallorders()
	{
		return orderrepo.findAll();
	}
	

}
