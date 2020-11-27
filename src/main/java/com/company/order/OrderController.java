package com.company.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/orders/{id}")
    public Order getOrderByOrderId(@PathVariable("id") Long id) {
        return orderService.getOrderByOrderId(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/orders")
    public Order addOrder(@RequestBody String rawBody) {
        return orderService.addOrder(rawBody);
    }
}
