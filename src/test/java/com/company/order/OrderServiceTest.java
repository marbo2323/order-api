package com.company.order;

import com.company.product.Product;
import com.company.product.ProductRepository;
import com.company.user.User;
import com.company.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService service = new OrderService();

    @Captor
    ArgumentCaptor<Order> captor;

    @Test
    public void processOrdersShouldChangeOrderStatus() {
        List<Order> orders = Arrays.asList(
                new Order("NEW", new Date()),
                new Order("NEW", new Date())
        );

        when(orderRepository.getByStatus("NEW")).thenReturn(orders);
        service.processOrders();
        verify(orderRepository,times(2)).save(any(Order.class));
    }

    @Test
    public void processOrdersByPassesProcessedOrders(){
        List<Order> orders = new ArrayList<>();

        when(orderRepository.getByStatus("NEW")).thenReturn(orders);
        service.processOrders();
        verify(orderRepository,never()).save(any(Order.class));
    }



    @Test
    public void getOrderByOrderIdShouldReturnOne() {
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(new Order()));
        assertThat(service.getOrderByOrderId(1L), instanceOf(Order.class));
        verify(orderRepository).findById(1L);
    }

    @Test
    @WithMockUser(username = "basicuser", authorities = {"USER"})
    public void addOrderShouldAddNewOrder() {
        String rawBody = "{\n" +
                "    \"productId\": \"1\"\n" +
                "}";

        User user = new User("basicuser", "password", "Test", "User1", "test1@company.com");
        Product product = new Product("Product1", null);

        when(userRepository.findByUsername("basicuser")).thenReturn(user);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        service.addOrder(rawBody);
        verify(orderRepository).save(captor.capture());
        assertEquals("Inserted order should be made by basicuser", captor.getValue().getUser().getUsername(), "basicuser");
        assertEquals("Inserted order should order product1", captor.getValue().getProduct().getName(), "Product1");
    }
}