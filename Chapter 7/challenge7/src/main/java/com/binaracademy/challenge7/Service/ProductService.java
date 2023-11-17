package com.binaracademy.challenge7.Service;

import com.binaracademy.challenge7.Model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Long productCode, Product product) throws Exception;

    void deleteProduct(Long productCode) throws Exception;
    List<Product> getAllProducts();
}

