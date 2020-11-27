package com.company.order;

public class OrdereNotFoundException extends RuntimeException {
    public OrdereNotFoundException(Long id) {
        super("Could not find order " + id);
    }
}
