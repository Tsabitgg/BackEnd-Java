package com.binaracademy.challenge4.Service;

import com.binaracademy.challenge4.Model.Orders;
import com.binaracademy.challenge4.Model.OrdersDetail;
import com.binaracademy.challenge4.Model.Product;

import java.util.List;
import java.util.Optional;
public interface OrdersService {
    void addNewOrders(Orders orders);

    void addnewOrdersDetail(OrdersDetail ordersDetail);

    List<OrdersDetail> getAllOrdersDetail();

}