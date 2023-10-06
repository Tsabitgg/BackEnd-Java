package com.binaracademy.challenge4.Service;

import com.binaracademy.challenge4.Model.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long productCode);
    List<Product> getAllProducts();

    Product getProductByCode(Long productCode);
}