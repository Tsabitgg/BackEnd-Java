package com.binaracademy.challenge5.Repository;

import com.binaracademy.challenge5.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
