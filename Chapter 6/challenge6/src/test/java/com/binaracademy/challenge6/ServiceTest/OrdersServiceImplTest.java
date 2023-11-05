package com.binaracademy.challenge6.ServiceTest;

import com.binaracademy.challenge6.Model.Orders;
import com.binaracademy.challenge6.Model.OrdersDetail;
import com.binaracademy.challenge6.Repository.OrdersDetailRepository;
import com.binaracademy.challenge6.Repository.OrdersRepository;
import com.binaracademy.challenge6.Service.OrdersServiceImpl;
//import net.sf.jasperreports.engine.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.JasperPrint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdersServiceImplTest {

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrdersDetailRepository ordersDetailRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddNewOrders() {
        Orders orders = new Orders();
        //mengatur repository untuk menyimpan objek
        Mockito.when(ordersRepository.save(any(Orders.class))).thenReturn(orders);

        Orders savedOrders = ordersService.addNewOrders(orders);

        assertEquals(orders, savedOrders);
    }

    @Test
    public void testAddNewOrdersDetail() {
        OrdersDetail ordersDetail = new OrdersDetail();
        //mengatur repository untuk menyimpan objek
        Mockito.when(ordersDetailRepository.save(any(OrdersDetail.class))).thenReturn(ordersDetail);

        OrdersDetail savedOrdersDetail = ordersService.addnewOrdersDetail(ordersDetail);

        assertEquals(ordersDetail, savedOrdersDetail);
    }

    @Test
    public void testGetAllOrders() {
        List<Orders> ordersList = new ArrayList<>();
        //mengatur repository untuk mendapatkan objek
        Mockito.when(ordersRepository.findAll()).thenReturn(ordersList);

        List<Orders> result = ordersService.getAllOrders();

        assertEquals(ordersList, result);
    }

    @Test
    public void testGetAllOrdersDetail() {
        List<OrdersDetail> ordersDetailList = new ArrayList<>();
        //mengatur repository untuk mendapatkan objek
        Mockito.when(ordersDetailRepository.findAll()).thenReturn(ordersDetailList);

        List<OrdersDetail> result = ordersService.getAllOrdersDetail();

        assertEquals(ordersDetailList, result);
    }

    @Test
    public void testExportReportPDF() throws Exception {
        String reportFormat = "pdf";
        //membuat OrdersDetail kosong untuk disimulasikan dari repository
        List<OrdersDetail> ordersDetailList = new ArrayList<>();
        Mockito.when(ordersDetailRepository.findAll()).thenReturn(ordersDetailList);

        //memanggil exportReport dengan format reportFormat
        String result = ordersService.exportReport(reportFormat);

        //memerika hasilnya tidak null, berarti report berhasil dibuat
        assertNotNull(result);
        //
        assertEquals("report generated in path : D:\\Kuliah\\sib binar\\Chapter 6\\challenge6\\target", result);
    }

    //sama seperti pdf, cuma beda format
    @Test
    public void testExportReportHTML() throws Exception {
        String reportFormat = "html";
        List<OrdersDetail> ordersDetailList = new ArrayList<>();
        Mockito.when(ordersDetailRepository.findAll()).thenReturn(ordersDetailList);

        String result = ordersService.exportReport(reportFormat);

        assertNotNull(result);
        assertEquals("report generated in path : D:\\Kuliah\\sib binar\\Chapter 6\\challenge6\\target", result);
    }

}

