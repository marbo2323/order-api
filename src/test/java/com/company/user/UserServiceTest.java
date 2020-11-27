package com.company.user;


import com.company.order.Order;
import com.company.product.Product;
import com.company.role.UserRole;
import com.company.role.UserRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserService service = new UserService();

    @Test
    public void getAllShouldReturnTwo() {
        List<User> users = Arrays.asList(
                new User(),
                new User()
        );

        when(repository.findAll()).thenReturn(users);

        assertEquals("getAll should return two users", 2, service.getAll().size());
        verify(repository).findAll();
    }

    @Test
    public void getUserByIdShouldReturnOne() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        assertThat(service.getUserById(1L), instanceOf(User.class));
        verify(repository).findById(1L);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByIdShouldThrowUserNotFoundException() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        service.getUserById(1L);
        verify(repository).findById(1L);
    }

    @Test
    public void getUserOrdersShouldReturnTwoOrders() throws NoSuchFieldException, IllegalAccessException {
        User user = new User("user1", "password", "Test", "User1", "test1@company.com");
        Order order1 = new Order("NEW", new Date());
        order1.setUser(user);
        order1.setProduct(new Product("Product1", null));
        Order order2 = new Order("NEW", new Date());
        order2.setUser(user);
        order2.setProduct(new Product("Product2", null));
        List<Order> orders = Arrays.asList(order1, order2);

        Field userOrderField = user.getClass().getDeclaredField("orders");
        userOrderField.setAccessible(true);
        userOrderField.set(user, orders);

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        assertEquals("user should have two orders", 2, service.getUserOrders(1L).size());
        verify(repository).findById(1L);
    }

    @Captor
    ArgumentCaptor<User> captor;

    @Test
    public void addUserShouldAddNewUser() {

        doAnswer(invocation -> {
            User user = (User) invocation.getArguments()[0];
            Field userIdField = user.getClass().getSuperclass().getDeclaredField("id");
            userIdField.setAccessible(true);
            userIdField.set(user, 1L);
            return null;
        }).when(repository).save(any(User.class));


        doAnswer(invocation -> {
            UserRole userRole = (UserRole) invocation.getArguments()[0];
            return null;
        }).when(userRoleRepository).save(any(UserRole.class));

        when(repository.findById(anyLong())).thenReturn(Optional.of(new User()));

        String rawBody = "{\n" +
                "    \"username\": \"user.name\",\n" +
                "    \"password\": \"password\",\n" +
                "    \"firstName\": \"User\",\n" +
                "    \"lastName\": \"Name1\",\n" +
                "    \"email\": \"user.name1@company.com\"\n" +
                "}";
        service.addUser(rawBody);
        verify(repository).save(captor.capture());
        assertEquals("Inserted username should be user.name", captor.getValue().getUsername(), "user.name");

    }
}