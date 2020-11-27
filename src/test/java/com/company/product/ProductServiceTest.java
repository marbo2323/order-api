package com.company.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service = new ProductService();

    @Test
    public void getAllProductsShouldReturnTwo() {
        List<Product> products = Arrays.asList(
                new Product("Product1", null),
                new Product("Product2", null)
        );
        when(repository.findAll()).thenReturn(products);
        assertEquals("getAll should return 2 products", 2, service.getAllProducts().size());

    }


    @Captor
    ArgumentCaptor<Product> captor;

    @Test
    public void addProductShouldAddProduct() {
        String rawBody = "{\n" +
                "    \"productName\":\"New Product\",\n" +
                "    \"description\":\"description for new product\"\n" +
                "}";

        doAnswer(invocation -> {
            Product product = (Product) invocation.getArguments()[0];
            Field productIdField = product.getClass().getSuperclass().getDeclaredField("id");
            productIdField.setAccessible(true);
            productIdField.set(product, 1L);
            return product;
        }).when(repository).save(any(Product.class));
        service.addProduct(rawBody);
        verify(repository).save(captor.capture());
        assertEquals("Inserted username should be user.name", captor.getValue().getName(), "New Product");

    }
}