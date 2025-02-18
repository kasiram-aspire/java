package com.eureka.Order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration   
public class WebClientConfig {
   // this configuration for inter communication between two projects
	 @Bean
	 public WebClient.Builder webClientBuilder()
	 {
		 return WebClient.builder();
	 }
}
