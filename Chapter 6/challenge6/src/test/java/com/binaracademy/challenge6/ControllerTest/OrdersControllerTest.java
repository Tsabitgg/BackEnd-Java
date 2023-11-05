package com.binaracademy.challenge6.ControllerTest;

import com.binaracademy.challenge6.Controller.OrdersController;
import com.binaracademy.challenge6.Model.Orders;
import com.binaracademy.challenge6.Model.OrdersDetail;
import com.binaracademy.challenge6.Model.Response.OrdersDetailResponse;
import com.binaracademy.challenge6.Model.Response.OrdersResponse;
import com.binaracademy.challenge6.Service.OrdersService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class OrdersControllerTest {

    @InjectMocks
    private OrdersController ordersController;

    @Mock
    private OrdersService ordersService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProduct() {
        Orders orders = new Orders();
        Orders createdOrders = new Orders();
        Mockito.when(ordersService.addNewOrders(any(Orders.class))).thenReturn(createdOrders);

        ResponseEntity<OrdersResponse> response = ordersController.addProduct(orders);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddNewOrdersDetail() {
        OrdersDetail ordersDetail = new OrdersDetail();

        ResponseEntity<Void> response = ordersController.addnewOrdersDetail(ordersDetail);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetAllOrdersDetail() {
        List<OrdersDetail> ordersDetails = new ArrayList<>();
        Mockito.when(ordersService.getAllOrdersDetail()).thenReturn(ordersDetails);

        ResponseEntity<List<OrdersDetailResponse>> response = ordersController.getAllOrdersDetail();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllOrders() {
        List<Orders> orders = new ArrayList<>();
        Mockito.when(ordersService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<OrdersResponse>> response = ordersController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGenerateReport() throws Exception {
        String format = "pdf";
        Mockito.when(ordersService.exportReport(eq(format))).thenReturn("Generated Report");

        String response = ordersController.generateReport(format);

        assertEquals("Generated Report", response);
    }
}
