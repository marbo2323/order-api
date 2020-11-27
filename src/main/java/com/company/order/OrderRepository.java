package com.company.order;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> getByStatus(String status);
}
