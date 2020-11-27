package com.company.user;

import com.company.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User addUser(@RequestBody String rawBody){
        return userService.addUser(rawBody);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}/orders")
    public List<Order> getUserOrders(@PathVariable("id") Long id){
        return userService.getUserOrders(id);
    }
}
