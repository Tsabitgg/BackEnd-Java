package com.binaracademy.challenge4.Service;

import com.binaracademy.challenge4.Model.Orders;
import com.binaracademy.challenge4.Model.OrdersDetail;
import com.binaracademy.challenge4.Model.Product;
import com.binaracademy.challenge4.Repository.OrdersDetailRepository;
import com.binaracademy.challenge4.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersDetailRepository ordersDetailRepository;


    @Override
    public void addNewOrders(Orders orders) {
        ordersRepository.save(orders);
    }

    @Override
    public void addnewOrdersDetail(OrdersDetail ordersDetail) {
        ordersDetailRepository.save(ordersDetail);
    }

    @Override
    public List<OrdersDetail> getAllOrdersDetail() {
        return ordersDetailRepository.findAll();
    }
}