package java.com.example;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.feign.OrderServiceClient;
import com.example.paymentservice.PaymentRepository;
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
class PaymentControllerEurekaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private OrderServiceClient orderServiceClient;

    @Test
    void getAllPaymentsEurekaTest() throws Exception {
        // Mock paymentRepository.findAll() to return a list of payments
        List<Payment> payments = Arrays.asList(new Payment(), new Payment());
        when(paymentRepository.findAll()).thenReturn(payments);

        // Perform a mock HTTP request to /payment/getAll
        mockMvc.perform(MockMvcRequestBuilders.get("/payment/getAll"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));
    }

    @Test
    void getPaymentByIdEurekaTest() throws Exception {
        // Mock paymentRepository.findById() to return the sample payment
        when(paymentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Payment()));

        // Perform a mock HTTP request to /payment/get/{paymentId}
        mockMvc.perform(MockMvcRequestBuilders.get("/payment/get/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{}"));
    }

    @Test
    void updatePaymentEurekaTest() throws Exception {
        // Mock paymentRepository.findById() to return the sample payment
        when(paymentRepository.findById(any(Long.class))).thenReturn(Optional.of(new Payment()));

        // Perform a mock HTTP request to /payment/update/{paymentId}
        mockMvc.perform(MockMvcRequestBuilders.put("/payment/update/1")
                .contentType("application/json")
                .content("{\"amount\": 60.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Payment updated successfully"));
    }

    @Test
    void deletePaymentEurekaTest() throws Exception {
        // Mock paymentRepository.existsById() and deleteById() to return true and perform deletion
        when(paymentRepository.existsById(any(Long.class))).thenReturn(true);

        // Perform a mock HTTP request to /payment/delete/{paymentId}
        mockMvc.perform(MockMvcRequestBuilders.delete("/payment/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Payment deleted successfully"));
    }
}
