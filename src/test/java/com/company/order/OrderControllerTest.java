package com.company.order;


import com.company.product.Product;
import com.company.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService service;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void getOrderByOrderIdShouldReturnOneOrder() throws Exception {
        User user = new User("user1", "password", "Test", "User1", "test1@company.com");
        Order order = new Order("NEW", new Date());
        order.setUser(user);
        order.setProduct(new Product("Mini", "Mini"));
        when(service.getOrderByOrderId(anyLong())).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(order.getStatus())));

        verify(service).getOrderByOrderId(anyLong());
    }

    @Test
    public void addOrderShouldAddNewOrder() throws Exception {
        User user = new User("user1", "password", "Test", "User1", "test1@company.com");
        Order order = new Order("NEW", new Date());
        order.setUser(user);
        order.setProduct(new Product("Mini", "Mini"));
        when(service.addOrder(anyString())).thenReturn(order);

        mockMvc.perform(post("/orders").content("order json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(order.getStatus())));

        verify(service).addOrder(anyString());
    }
}