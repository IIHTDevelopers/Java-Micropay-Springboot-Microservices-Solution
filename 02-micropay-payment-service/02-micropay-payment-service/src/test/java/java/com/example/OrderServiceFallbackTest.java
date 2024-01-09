package java.com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceFallbackTest {

    @Test
    void updateOrderStatusFallbackTest() {
        OrderServiceFallback fallback = new OrderServiceFallback();

        Long userId = 101L;
        Double amount = 50.0;

        String fallbackMessage = fallback.updateOrderStatus(userId, amount);

        // Assert that the fallback message is returned when Order Service is
        // unavailable
        assertEquals("Order Service is currently unavailable. Please try again later.", fallbackMessage);
    }

    // Additional test cases can be added based on specific fallback scenarios or
    // requirements
}
