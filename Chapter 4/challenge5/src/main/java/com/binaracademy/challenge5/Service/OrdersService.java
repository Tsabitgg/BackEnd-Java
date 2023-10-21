package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.Orders;
import com.binaracademy.challenge5.Model.OrdersDetail;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface OrdersService {
    Orders addNewOrders(Orders orders);

    OrdersDetail addnewOrdersDetail(OrdersDetail ordersDetail);
    List<Orders> getAllOrders();

    List<OrdersDetail> getAllOrdersDetail();

    String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}