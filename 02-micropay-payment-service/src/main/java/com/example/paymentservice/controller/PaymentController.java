package com.example.paymentservice.controller;

import com.example.paymentservice.PaymentRepository;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.feign.OrderServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderServiceClient orderServiceClient;

    @PostMapping("/makePayment")
    public ResponseEntity<String> makePayment(@RequestParam("userId") Long userId, @RequestParam("amount") Double amount) {
        // Your payment processing logic here

        // Save payment to the database
        Payment payment = new Payment(userId, amount);
        paymentRepository.save(payment);

        // Use Feign client to update order status
        String orderStatus = orderServiceClient.updateOrderStatus(userId, amount);

        return ResponseEntity.ok("Payment processed successfully. Order status: " + orderStatus);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/get/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{paymentId}")
    public ResponseEntity<String> updatePayment(@PathVariable Long paymentId, @RequestBody Payment updatedPayment) {
        Optional<Payment> existingPaymentOptional = paymentRepository.findById(paymentId);

        if (existingPaymentOptional.isPresent()) {
            Payment existingPayment = existingPaymentOptional.get();
            // Update payment details
            existingPayment.setAmount(updatedPayment.getAmount());
            // ... other fields
            paymentRepository.save(existingPayment);
            return ResponseEntity.ok("Payment updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) {
        if (paymentRepository.existsById(paymentId)) {
            paymentRepository.deleteById(paymentId);
            return ResponseEntity.ok("Payment deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
