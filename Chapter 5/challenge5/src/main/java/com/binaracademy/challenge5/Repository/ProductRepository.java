package com.binaracademy.challenge5.Repository;

import com.binaracademy.challenge5.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
