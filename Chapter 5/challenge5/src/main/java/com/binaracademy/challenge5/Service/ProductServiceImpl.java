package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.Product;
import com.binaracademy.challenge5.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productCode, Product product) throws Exception {      //sama seperti updatemerchantstatus
        Optional<Product> existingProduct = productRepository.findById(productCode);

        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setPrice(product.getPrice());
            return productRepository.save(updatedProduct);
        } else {
            throw new Exception("Product with code " + productCode + " not found");
        }
    }

    @Override
    public void deleteProduct(Long productCode) throws Exception {
        Optional<Product> product = productRepository.findById(productCode);

        if (product.isPresent()) {
            productRepository.delete(product.get());
        } else {
            throw new Exception("Product with code " + productCode + " not found");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}


