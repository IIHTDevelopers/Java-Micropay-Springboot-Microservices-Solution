package java.com.example;

import com.example.paymentservice.controller.PaymentController;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.feign.OrderServiceClient;
import com.example.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.*;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentControllerTest {

    private static PaymentController paymentController;
    private static PaymentRepository paymentRepository;
    private static OrderServiceClient orderServiceClient;

    @BeforeAll
    static void setUp() {
        paymentRepository = new TestPaymentRepository(); // You need to implement TestPaymentRepository
        orderServiceClient = new TestOrderServiceClient(); // You need to implement TestOrderServiceClient
        paymentController = new PaymentController();
        paymentController.paymentRepository = paymentRepository;
        paymentController.orderServiceClient = orderServiceClient;
    }

    @AfterAll
    static void tearDown() {
        // Any cleanup code if needed
    }

    @Test
    void testMakePayment() {
        // Arrange
        Long userId = 1L;
        Double amount = 50.0;

        // Act
        ResponseEntity<String> responseEntity = paymentController.makePayment(userId, amount);

        // Assert
        assertEquals("Payment processed successfully. Order status: Order Processing", responseEntity.getBody());
    }

    // Add more test cases for other methods in PaymentController if needed
}
