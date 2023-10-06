package com.binaracademy.challenge4.Repository;
import com.binaracademy.challenge4.Model.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {

    @Query("SELECT od FROM OrdersDetail od WHERE od.orders.users.usersId = :userId")
    List<OrdersDetail> findOrderDetailsByUserId(@Param("userId") Long userId);

}