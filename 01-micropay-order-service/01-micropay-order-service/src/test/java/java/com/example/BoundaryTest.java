package java.com.example;

import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.feign.PaymentServiceClient;
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
class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentServiceClient paymentServiceClient;

    @InjectMocks
    private OrderController orderController;

    private Order sampleOrder;

    @BeforeAll
    static void setUp() {
        // Any setup that needs to be done before all test cases
    }

    @BeforeEach
    void init() {
        // Initialize sample data or objects
        sampleOrder = new Order();
        sampleOrder.setId(1L);
        sampleOrder.setUserId(101L);
        sampleOrder.setAmount(50.0);
    }

    @Test
    void createOrder() {
        // Mock orderRepository.save() to return the sample order
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(sampleOrder);

        // Mock paymentServiceClient.makePayment() to return a success response
        Mockito.when(paymentServiceClient.makePayment(Mockito.anyLong(), Mockito.anyDouble()))
                .thenReturn("Payment successful");

        // Perform the controller method invocation
        ResponseEntity<String> responseEntity = orderController.createOrder(sampleOrder);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Order created successfully", responseEntity.getBody());
    }

    @Test
    void getAllOrders() {
        // Mock orderRepository.findAll() to return a list of orders
        List<Order> orders = Arrays.asList(sampleOrder, new Order());
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        // Perform the controller method invocation
        ResponseEntity<List<Order>> responseEntity = orderController.getAllOrders();

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(orders, responseEntity.getBody());
    }

    @Test
    void getOrderById() {
        // Mock orderRepository.findById() to return the sample order
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(sampleOrder));

        // Perform the controller method invocation
        ResponseEntity<Order> responseEntity = orderController.getOrderById(1L);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(sampleOrder, responseEntity.getBody());
    }

    @Test
    void updateOrder() {
        // Mock orderRepository.findById() to return the sample order
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(sampleOrder));

        // Perform the controller method invocation
        ResponseEntity<String> responseEntity = orderController.updateOrder(1L, sampleOrder);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Order updated successfully", responseEntity.getBody());
    }

    @Test
    void deleteOrder() {
        // Mock orderRepository.existsById() and deleteById() to return true and perform
        // deletion
        Mockito.when(orderRepository.existsById(Mockito.anyLong())).thenReturn(true);

        // Perform the controller method invocation
        ResponseEntity<String> responseEntity = orderController.deleteOrder(1L);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Order deleted successfully", responseEntity.getBody());
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
