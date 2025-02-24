package com.eureka.api_gateway_service.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private RouteValidator validator;
//     @Autowired
     private JwtService jwtService;
    @Autowired
    private RestTemplate template;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
        // Empty config class
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            
            if (validator.isSecured.test(exchange.getRequest())) {
                
                // Check if Authorization header exists
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.error("Missing Authorization header");
                    throw new RuntimeException("Missing Authorization header");
                }

                // Extract token
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    logger.error("Invalid Authorization header format: {}", authHeader);
                    throw new RuntimeException("Invalid Authorization header");
                }

                // Extract Bearer token (keep the full "Bearer {token}" format)
                logger.info("Extracted Authorization header: {}", authHeader);

                try {
                    // Create headers for auth service request
                    HttpHeaders headers = new HttpHeaders();
                    headers.set(HttpHeaders.AUTHORIZATION, authHeader); // Forward the exact token

                    HttpEntity<String> entity = new HttpEntity<>(headers);

                    // Call auth service to validate token
                    ResponseEntity<String> response = template.exchange(
                        "http://localhost:9898/auth/validatetoken",
                        HttpMethod.POST,
                        entity,
                        String.class
                    );

                    logger.info("Auth Service Response: {}", response.getStatusCode());
                   
                    // If token validation fails, throw an error
                    if (!response.getStatusCode().is2xxSuccessful()) {
                        logger.error("Unauthorized access: Invalid token");
                        throw new RuntimeException("Unauthorized access to application");
                    }
                    ResponseEntity<String> responserole = template.exchange(
                            "http://localhost:9898/auth/userrole",
                            HttpMethod.POST,
                            entity,
                            String.class
                        );
                    String userRole = responserole.getBody();  
                    logger.info("Extracted User Role: {}", userRole);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-User-Role", userRole)
                            .build();

                        return chain.filter(exchange.mutate().request(modifiedRequest).build());

                } catch (Exception e) {
                    logger.error("Exception while validating token: {}", e.getMessage());
                    throw new RuntimeException("Unauthorized access to application");
                }
               
                
               
            }
            return chain.filter(exchange); // Continue request processing if token is valid
        });
    }
}
