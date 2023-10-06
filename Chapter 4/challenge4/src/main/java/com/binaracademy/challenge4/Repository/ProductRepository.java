package com.binaracademy.challenge4.Repository;

import com.binaracademy.challenge4.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT productCode, productName, price FROM Product")
    List<Product> findByProductName(String productName);

}
