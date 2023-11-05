package com.binaracademy.challenge5.ServiceTest;

import com.binaracademy.challenge5.Model.Product;
import com.binaracademy.challenge5.Repository.ProductRepository;
import com.binaracademy.challenge5.Service.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.addProduct(product);

        assertEquals(product, savedProduct);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long productCode = 123L;
        Product product = new Product();
        product.setProductName("Updated Product");
        product.setPrice(100.0);

        Optional<Product> existingProduct = Optional.of(new Product());
        existingProduct.get().setProductCode(productCode);

        Mockito.when(productRepository.findById(productCode)).thenReturn(existingProduct);
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProduct(productCode, product);

        assertEquals(product.getProductName(), updatedProduct.getProductName());
        assertEquals(product.getPrice(), updatedProduct.getPrice(), 0.001); // Use delta for double comparison
    }

    @Test(expected = Exception.class)
    public void testUpdateProductProductNotFound() throws Exception {
        Long productCode = 123L;
        Product product = new Product();

        Mockito.when(productRepository.findById(productCode)).thenReturn(Optional.empty());

        productService.updateProduct(productCode, product);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productCode = 123L;
        Optional<Product> product = Optional.of(new Product());

        Mockito.when(productRepository.findById(productCode)).thenReturn(product);

        productService.deleteProduct(productCode);

        Mockito.verify(productRepository).delete(product.get());
    }

    @Test(expected = Exception.class)
    public void testDeleteProductProductNotFound() throws Exception {
        Long productCode = 123L;

        Mockito.when(productRepository.findById(productCode)).thenReturn(Optional.empty());

        productService.deleteProduct(productCode);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(productList, result);
    }
}
