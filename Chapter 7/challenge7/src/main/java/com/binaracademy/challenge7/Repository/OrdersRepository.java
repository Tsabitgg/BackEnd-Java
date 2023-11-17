package com.binaracademy.challenge7.Repository;

import com.binaracademy.challenge7.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
