package com.binaracademy.challenge7.Service;

import com.binaracademy.challenge7.Model.Orders;
import com.binaracademy.challenge7.Model.OrdersDetail;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface OrdersService {
    Orders addNewOrders(Orders orders);

    OrdersDetail addnewOrdersDetail(OrdersDetail ordersDetail);
    List<Orders> getAllOrders();

    List<OrdersDetail> getAllOrdersDetail();
    void deleteOrders(Long orderId) throws Exception;
    String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}