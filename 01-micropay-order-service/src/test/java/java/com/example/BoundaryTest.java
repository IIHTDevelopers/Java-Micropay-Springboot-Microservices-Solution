package java.com.example;

import com.example.orderservice.controller.OrderController;
import com.example.orderservice.entity.Order;
import com.example.orderservice.feign.PaymentServiceClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderControllerTest {

    private static OrderController orderController;

    @BeforeAll
    static void setUp() {
        orderController = new OrderController();
        orderController.orderRepository = new TestOrderRepository(); // You need to implement TestOrderRepository
        orderController.paymentServiceClient = new TestPaymentServiceClient(); // You need to implement
                                                                               // TestPaymentServiceClient
    }

    @AfterAll
    static void tearDown() {
        // Any cleanup code if needed
    }

    @Test
    void testCreateOrderWithMaximumAmount() {
        // Arrange
        double maxAmount = Double.MAX_VALUE;
        Order order = new Order(1L, maxAmount);

        // Act
        ResponseEntity<String> responseEntity = orderController.createOrder(order);

        // Assert
        assertEquals("Order created successfully", responseEntity.getBody());
    }

    @Test
    void testCreateOrderWithZeroAmount() {
        // Arrange
        double zeroAmount = 0.0;
        Order order = new Order(1L, zeroAmount);

        // Act
        ResponseEntity<String> responseEntity = orderController.createOrder(order);

        // Assert
        assertEquals("Order created successfully", responseEntity.getBody());
    }

    // Add more boundary test cases for createOrder method as needed
}
