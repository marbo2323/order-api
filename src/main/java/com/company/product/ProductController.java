package com.company.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    @GetMapping(path = "/products")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/products")
    public Product addProduct(@RequestBody String rawBody) {
        return productService.addProduct(rawBody);
    }

}
