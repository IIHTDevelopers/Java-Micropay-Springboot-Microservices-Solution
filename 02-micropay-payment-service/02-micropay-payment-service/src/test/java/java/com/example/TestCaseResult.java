package java.com.example;

import com.example.paymentservice.entity.Payment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Long userId = 123L;
        Double amount = 45.67;

        // Act
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);

        // Assert
        assertEquals(userId, payment.getUserId());
        assertEquals(amount, payment.getAmount());
    }

    // Add more test cases for other methods or behaviors as needed
}
