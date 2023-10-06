package com.binaracademy.challenge4.Repository;

import com.binaracademy.challenge4.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o WHERE o.users.usersId = :userId AND o.completed = true")
    List<Orders> findCompletedOrdersByUserId(@Param("userId") Long userId);

}
