package com.binaracademy.challenge6.Service;

import com.binaracademy.challenge6.Model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Long productCode, Product product) throws Exception;

    void deleteProduct(Long productCode) throws Exception;
    List<Product> getAllProducts();
}

