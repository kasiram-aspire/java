package com.eureka.Product_service.model;

import java.net.InetAddress;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DummyServiceHealthIndicator implements HealthIndicator {
	private static final String CHECK_ADDRESS = "google.com";
   
	@Override
    public Health health() {
        // Simulate service is always UP (change to DOWN for testing)
        boolean isServiceUp = false;
        boolean isReachable = isAddressReachable(CHECK_ADDRESS);
        if (isServiceUp) {
            return Health.up().withDetail("Dummy Service", "Running").build();
        }
        else if (isReachable) {
            return Health.up().withDetail("Dummy Service", "Running").build();
        } 
        else {
            return Health.down().withDetail("Dummy Service", "Not Available").build();
        }
    }
    private boolean isAddressReachable(String address) {
        try {
            InetAddress inet = InetAddress.getByName(address);
            System.out.println(inet);
            return inet.isReachable(3000); // Timeout in milliseconds
        } catch (Exception e) {
            return false;
        }
    }
}

