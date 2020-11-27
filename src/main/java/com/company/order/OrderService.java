package com.company.order;

import com.company.product.Product;
import com.company.product.ProductNotFoundExeption;
import com.company.product.ProductRepository;
import com.company.user.User;
import com.company.user.UserRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableAsync
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Async
    @Scheduled(initialDelay = 3000L, fixedDelay = 3000L)
    public void processOrders() {
        List<Order> newOrders = orderRepository.getByStatus(Order.STATUS_NEW);
        if (newOrders.isEmpty()) {
            logger.info("No new orders.");
        }
        for (Order order : newOrders) {
            logger.info("Updating order status for: " + order.getId());
            order.setStatus(Order.STATUS_COMPLETED);
            orderRepository.save(order);
        }
    }

    public Order getOrderByOrderId(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrdereNotFoundException(id));
    }

    public Order addOrder(String rawBody) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        JSONObject jsonObject = new JSONObject(rawBody);
        Long productId = Long.parseLong(jsonObject.getString("productId"));

        User user = userRepository.findByUsername(username);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundExeption(productId));
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setProduct(product);
        return orderRepository.save(newOrder);
    }
}
