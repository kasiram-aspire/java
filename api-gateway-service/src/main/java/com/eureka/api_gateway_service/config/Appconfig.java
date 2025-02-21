package com.eureka.api_gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Appconfig {
	@Bean
     public RestTemplate template()
     {
    	 return new RestTemplate();
     }
}
