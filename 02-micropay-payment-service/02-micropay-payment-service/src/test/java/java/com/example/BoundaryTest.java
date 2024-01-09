package java.com.example;

import com.example.paymentservice.PaymentRepository;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.feign.OrderServiceClient;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class BoundaryTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderServiceClient orderServiceClient;

    @InjectMocks
    private PaymentController paymentController;

    private Payment samplePayment;

    @BeforeAll
    static void setUp() {
        // Any setup that needs to be done before all test cases
    }

    @BeforeEach
    void init() {
        // Initialize sample data or objects
        samplePayment = new Payment();
        samplePayment.setId(1L);
        samplePayment.setUserId(101L);
        samplePayment.setAmount(50.0);
    }

    @Test
    void makePayment() {
        // Mock paymentRepository.save() to return the sample payment
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(samplePayment);

        // Mock orderServiceClient.updateOrderStatus() to return a success response
        Mockito.when(orderServiceClient.updateOrderStatus(Mockito.anyLong(), Mockito.anyDouble()))
                .thenReturn("SUCCESS");

        // Perform the controller method invocation
        ResponseEntity<String> responseEntity = paymentController.makePayment(101L, 50.0);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody().contains("Payment processed successfully"));
        Assertions.assertTrue(responseEntity.getBody().contains("Order status: SUCCESS"));
    }

    @Test
    void getAllPayments() {
        // Mock paymentRepository.findAll() to return a list of payments
        List<Payment> payments = Arrays.asList(samplePayment, new Payment());
        Mockito.when(paymentRepository.findAll()).thenReturn(payments);

        // Perform the controller method invocation
        ResponseEntity<List<Payment>> responseEntity = paymentController.getAllPayments();

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(payments, responseEntity.getBody());
    }

    @Test
    void getPaymentById() {
        // Mock paymentRepository.findById() to return the sample payment
        Mockito.when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(samplePayment));

        // Perform the controller method invocation
        ResponseEntity<Payment> responseEntity = paymentController.getPaymentById(1L);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(samplePayment, responseEntity.getBody());
    }

    @Test
    void updatePayment() {
        // Mock paymentRepository.findById() to return the sample payment
        Mockito.when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(samplePayment));

        // Perform the controller method invocation
        ResponseEntity<String> responseEntity = paymentController.updatePayment(1L, samplePayment);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Payment updated successfully", responseEntity.getBody());
    }

    @Test
    void deletePayment() {
        // Mock paymentRepository.existsById() and deleteById() to return true and
        // perform deletion
        Mockito.when(paymentRepository.existsById(Mockito.anyLong())).thenReturn(true);

        // Perform the controller method invocation
        ResponseEntity<String> responseEntity = paymentController.deletePayment(1L);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Payment deleted successfully", responseEntity.getBody());
    }

    @AfterEach
    void tearDown() {
        // Any cleanup that needs to be done after each test case
    }

    @AfterAll
    static void tearDownAll() {
        // Any cleanup that needs to be done after all test cases
    }
}
