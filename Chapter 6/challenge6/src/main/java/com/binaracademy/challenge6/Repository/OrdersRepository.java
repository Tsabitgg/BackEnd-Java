package com.binaracademy.challenge6.Repository;

import com.binaracademy.challenge6.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
