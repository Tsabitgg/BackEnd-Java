package com.binaracademy.challenge7.Repository;

import com.binaracademy.challenge7.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
