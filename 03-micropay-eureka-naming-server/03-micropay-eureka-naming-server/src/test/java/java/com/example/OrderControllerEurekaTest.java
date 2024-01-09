import com.example.orderservice.entity.Order;
import com.example.orderservice.feign.PaymentServiceClient;
import com.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerEurekaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private PaymentServiceClient paymentServiceClient;

    @Test
    void createOrderEurekaTest() throws Exception {
        // Mock orderRepository.save() to return the sample order
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        // Mock paymentServiceClient.makePayment() to return a success response
        when(paymentServiceClient.makePayment(any(Long.class), any(Double.class))).thenReturn("Payment successful");

        // Perform a mock HTTP request to /order/create
        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                .contentType("application/json")
                .content("{\"userId\": 101, \"amount\": 50.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order created successfully"));
    }

    @Test
    void getAllOrdersEurekaTest() throws Exception {
        // Mock orderRepository.findAll() to return a list of orders
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        // Perform a mock HTTP request to /order/getAll
        mockMvc.perform(MockMvcRequestBuilders.get("/order/getAll"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));
    }

    @Test
    void getOrderByIdEurekaTest() throws Exception {
        // Mock orderRepository.findById() to return the sample order
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(new Order()));

        // Perform a mock HTTP request to /order/get/{orderId}
        mockMvc.perform(MockMvcRequestBuilders.get("/order/get/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void updateOrderEurekaTest() throws Exception {
        // Mock orderRepository.findById() to return the sample order
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(new Order()));

        // Perform a mock HTTP request to /order/update/{orderId}
        mockMvc.perform(MockMvcRequestBuilders.put("/order/update/1")
                .contentType("application/json")
                .content("{\"amount\": 60.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order updated successfully"));
    }

    @Test
    void deleteOrderEurekaTest() throws Exception {
        // Mock orderRepository.existsById() and deleteById() to return true and perform deletion
        when(orderRepository.existsById(any(Long.class))).thenReturn(true);

        // Perform a mock HTTP request to /order/delete/{orderId}
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));
    }
}
