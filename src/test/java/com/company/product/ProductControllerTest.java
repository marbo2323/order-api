package com.company.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService service;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void getProductsShouldReturnAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product("Mini", "Mini"),
                new Product("Standard", "Standard")
        );
        when(service.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(products.get(0).getName())));

        verify(service).getAllProducts();
    }

    @Test
    public void addProductShouldAddProductAndReturnTheProduct() throws Exception {
        Product product = new Product("Mini", "Mini");
        when(service.addProduct(anyString())).thenReturn(product);

        mockMvc.perform(post("/products").content("product json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(product.getName())));

        verify(service).addProduct(anyString());
    }
}