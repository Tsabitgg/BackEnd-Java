package com.binaracademy.challenge7.Service;

import com.binaracademy.challenge7.Model.Orders;
import com.binaracademy.challenge7.Model.OrdersDetail;
import com.binaracademy.challenge7.Repository.OrdersDetailRepository;
import com.binaracademy.challenge7.Repository.OrdersRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrdersDetailRepository ordersDetailRepository;


    @Transactional
    @Override
    public Orders addNewOrders(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Transactional
    @Override
    public OrdersDetail addnewOrdersDetail(OrdersDetail ordersDetail) {
        return ordersDetailRepository.save(ordersDetail);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrdersDetail> getAllOrdersDetail() {
        return ordersDetailRepository.findAll();
    }

    @Override
    public void deleteOrders(Long orderId) throws Exception {
        Optional<Orders> orders = ordersRepository.findById(orderId);

        if (orders.isPresent()) {
            ordersRepository.delete(orders.get());
        } else {
            throw new Exception("Product with code " + orderId + " not found");
        }
    }

    //schedular
    @Scheduled(cron = "0 0 0 * * ?") //dijalankan setiap hari pukul 00:00:00
    public void scheduledDeleteOrders() {
        try {
            //menghapus orderId 1
            deleteOrders(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "D:\\Kuliah\\sib binar\\Chapter 6\\challenge6\\target";

        List<OrdersDetail> ordersDetail =  ordersDetailRepository.findAll();

        File file = ResourceUtils.getFile("classpath:Invoice_Binarfud.jrxml");                        //mengambil file jrxml
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());                     //compile jrxml menjadi jasperreport
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ordersDetail);                       //mengambil orderdetails sebagai datasource
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Tsabitgg");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);               //mengisi jaspereport dengan datasource
        if (reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\Invoice_Binarfud.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\Invoice_Binarfud.pdf");
        }

        return "report generated in path : " + path;
    }
}