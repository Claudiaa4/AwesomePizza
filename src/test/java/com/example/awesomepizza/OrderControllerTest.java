package com.example.awesomepizza;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.awesomepizza.controller.OrderController;
import com.example.awesomepizza.model.Order;
import com.example.awesomepizza.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order("Margherita", "Claudia");
        given(orderService.createOrder(any(), any())).willReturn(order);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pizzaDetails\":\"Margherita\", \"customerName\":\"Claudia\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pizzaDetails").value("Margherita"))
                .andExpect(jsonPath("$.customerName").value("Claudia"));
    }

    @Test
    public void testGetOrdersInQueue() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        given(orderService.getOrdersInQueue()).willReturn(orders);

        mockMvc.perform(get("/orders/queue"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        Order order = new Order();
        given(orderService.updateOrderStatus(any(), any())).willReturn(order);

        mockMvc.perform(put("/orders/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"IN_PROGRESS\""))
                .andExpect(status().isOk());
    }
}
