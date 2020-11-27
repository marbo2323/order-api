package com.company.user;

import com.company.order.Order;
import com.company.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllShouldReturnAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("user1", "password", "Test", "User1", "test1@company.com"),
                new User("user2", "password", "Test", "User2", "test2@company.com")
        );

        when(service.getAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(users.get(0).getUsername())));

        verify(service).getAll();
    }

    @Test
    public void addUserShouldReturnNewCreatedUser() throws Exception {
        User user = new User("user1", "password", "Test", "User1", "test1@company.com");
        when(service.addUser(any(String.class))).thenReturn(user);

        mockMvc.perform(post("/users").content("user json"))
                .andExpect(status().isOk());

        verify(service).addUser(anyString());
    }

    @Test
    public void getUserByIdShouldReturnOneUser() throws Exception {
        User user = new User("user1", "password", "Test", "User1", "test1@company.com");
        when(service.getUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(user.getUsername())));

        verify(service).getUserById(anyLong());
    }

    @Test
    public void getUserOrdersShouldReturnOrdersOfGivenUser() throws Exception {
        User user = new User("user1", "password", "Test", "User1", "test1@company.com");
        Order order1 = new Order("NEW", new Date());
        order1.setUser(user);
        order1.setProduct(new Product("Mini", "Mini"));
        Order order2 = new Order("NEW", new Date());
        order2.setUser(user);
        order2.setProduct(new Product("Standard", "Standard"));

        List<Order> orders = Arrays.asList(order1, order2);
        when(service.getUserOrders(anyLong())).thenReturn(orders);

        mockMvc.perform(get("/users/1/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].product.name", is(orders.get(0).getProduct().getName())));

        verify(service).getUserOrders(anyLong());
    }

}