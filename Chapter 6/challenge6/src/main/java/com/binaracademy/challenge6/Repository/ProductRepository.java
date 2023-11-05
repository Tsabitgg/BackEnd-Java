package com.binaracademy.challenge6.Repository;

import com.binaracademy.challenge6.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
