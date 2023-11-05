package com.binaracademy.challenge6.Controller;

import com.binaracademy.challenge6.Model.Orders;
import com.binaracademy.challenge6.Model.OrdersDetail;
import com.binaracademy.challenge6.Model.Response.OrdersDetailResponse;
import com.binaracademy.challenge6.Model.Response.OrdersResponse;
import com.binaracademy.challenge6.Service.OrdersService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping(value = "/add-orders", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrdersResponse> addProduct(@RequestBody Orders orders) {
        Orders createdOrders = ordersService.addNewOrders(orders);
        OrdersResponse ordersResponse = createOrdersResponse(createdOrders);
        return new ResponseEntity<>(ordersResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/add-orders-detail", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> addnewOrdersDetail(@RequestBody OrdersDetail ordersDetail) {
        ordersService.addnewOrdersDetail(ordersDetail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/get-all-ordersdetail", produces = "application/json")
    public ResponseEntity<List<OrdersDetailResponse>> getAllOrdersDetail() {
        List<OrdersDetail> ordersDetails = ordersService.getAllOrdersDetail();
        List<OrdersDetailResponse> ordersDetailResponses = ordersDetails.stream()
                .map(this::createOrdersDetailResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ordersDetailResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-orders", produces = "application/json")
    public ResponseEntity<List<OrdersResponse>> getAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        List<OrdersResponse> ordersResponses = orders.stream()
                .map(this::createOrdersResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ordersResponses, HttpStatus.OK);
    }

    private OrdersResponse createOrdersResponse(Orders orders) {
        OrdersResponse ordersResponse = new OrdersResponse();
        ordersResponse.setOrderId(orders.getOrderId());
        ordersResponse.setOrderTime(orders.getOrderTime());
        ordersResponse.setUserId(orders.getUser().getUserId());
        ordersResponse.setDestinationAddress(orders.getDestinationAddress());
        ordersResponse.setCompleted(orders.isCompleted());
        // Set properti lain sesuai kebutuhan
        return ordersResponse;
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
        return ordersService.exportReport(format);
    }

    // membuat objek OrdersDetailResponse dari entitas OrdersDetail
    private OrdersDetailResponse createOrdersDetailResponse(OrdersDetail ordersDetail) {
        OrdersDetailResponse ordersDetailResponse = new OrdersDetailResponse();
        ordersDetailResponse.setOrderDetailId(ordersDetail.getOrderDetailId());
        ordersDetailResponse.setOrderId(ordersDetail.getOrders().getOrderId());
        ordersDetailResponse.setProductCode(ordersDetail.getProduct().getProductCode());
        ordersDetailResponse.setQuantity(ordersDetail.getQuantity());
        ordersDetailResponse.setTotalPrice(ordersDetail.getTotalPrice());
        return ordersDetailResponse;
    }
}