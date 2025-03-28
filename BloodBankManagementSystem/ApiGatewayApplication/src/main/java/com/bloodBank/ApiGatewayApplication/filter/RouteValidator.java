package com.bloodBank.ApiGatewayApplication.filter;

import java.util.List;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import java.util.function.Predicate;
@Component
public class RouteValidator {
	public static final List<String> openApiEndpoints=List.of(
			"/auth/adduser",
			"auth/login",
			"auth/refresh-token",
			"/eureka",
			"/api/email/send",
			"/auth/uploadUsers"
			);
	 public Predicate<ServerHttpRequest> isSecured =
	            request -> openApiEndpoints.stream()
	                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
