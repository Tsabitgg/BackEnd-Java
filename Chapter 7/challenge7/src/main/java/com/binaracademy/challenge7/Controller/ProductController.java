package com.binaracademy.challenge7.Controller;

import com.binaracademy.challenge7.Model.Product;
import com.binaracademy.challenge7.Model.Response.ProductResponse;
import com.binaracademy.challenge7.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/add-product", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        ProductResponse productResponse = createProductResponse(createdProduct);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }


    @PutMapping(value = "/update/{productCode}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productCode, @RequestBody Product product) throws Exception {
        Product updatedProduct = productService.updateProduct(productCode, product);
        ProductResponse productResponse = createProductResponse(updatedProduct);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{productCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productCode) throws Exception {
        productService.deleteProduct(productCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/get-all-product", produces = "application/json")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productResponses = products.stream()
                .map(this::createProductResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    // membuat objek ProductResponse dari entitas Product
    private ProductResponse createProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductCode(product.getProductCode());
        productResponse.setProductName(product.getProductName());
        productResponse.setPrice(product.getPrice());
        productResponse.setMerchantCode(product.getMerchant().getMerchantCode());
        return productResponse;
    }
}

