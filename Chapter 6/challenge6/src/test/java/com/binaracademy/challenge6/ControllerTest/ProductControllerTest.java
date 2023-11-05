package com.binaracademy.challenge6.ControllerTest;

import com.binaracademy.challenge6.Controller.ProductController;
import com.binaracademy.challenge6.Model.Product;
import com.binaracademy.challenge6.Model.Response.ProductResponse;
import com.binaracademy.challenge6.Service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        Product createdProduct = new Product();
        Mockito.when(productService.addProduct(any(Product.class))).thenReturn(createdProduct);

        ResponseEntity<ProductResponse> response = productController.addProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long productCode = 123L;
        Product product = new Product();
        Product updatedProduct = new Product();
        Mockito.when(productService.updateProduct(eq(productCode), any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<ProductResponse> response = productController.updateProduct(productCode, product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productCode = 123L;

        ResponseEntity<Void> response = productController.deleteProduct(productCode);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        Mockito.when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ProductResponse>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
