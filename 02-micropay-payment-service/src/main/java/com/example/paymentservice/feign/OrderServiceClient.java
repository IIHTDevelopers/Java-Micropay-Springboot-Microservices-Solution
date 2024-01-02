package com.example.paymentservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", fallback = OrderServiceFallback.class)
public interface OrderServiceClient {

    @PostMapping("/order/updateOrderStatus")
    String updateOrderStatus(@RequestParam("userId") Long userId, @RequestParam("amount") Double amount);
}
