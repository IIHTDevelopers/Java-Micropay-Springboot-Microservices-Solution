package com.example.paymentservice.feign;

import org.springframework.stereotype.Component;

@Component
public class OrderServiceFallback implements OrderServiceClient {

    @Override
    public String updateOrderStatus(Long userId, Double amount) {
        // Fallback logic when the Order Service is unavailable
        return "Order Service is currently unavailable. Please try again later.";
    }
}
