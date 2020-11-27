package com.company.product;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product addProduct(String rawBody) {
        JSONObject jsonObject = new JSONObject(rawBody);
        String productName = jsonObject.getString("productName");
        String description = jsonObject.getString("description");
        Product product = new Product(productName, description);
        return productRepository.save(product);
    }
}
