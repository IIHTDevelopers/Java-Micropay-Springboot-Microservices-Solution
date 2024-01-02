package com.example.orderservice.fallback;

import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentServiceClient {

    @Override
    public String makePayment(Long userId, Double amount) {
        // Fallback logic when the Payment Service is unavailable
        return "Payment Service is currently unavailable. Please try again later.";
    }
}
