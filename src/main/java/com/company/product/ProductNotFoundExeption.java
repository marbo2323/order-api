package com.company.product;

public class ProductNotFoundExeption extends RuntimeException {
    public ProductNotFoundExeption(Long id) {
        super("Could not find product " + id);
    }
}
