package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.Orders;
import com.binaracademy.challenge5.Model.OrdersDetail;
import com.binaracademy.challenge5.Repository.OrdersDetailRepository;
import com.binaracademy.challenge5.Repository.OrdersRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrdersDetailRepository ordersDetailRepository;


    @Override
    public Orders addNewOrders(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public OrdersDetail addnewOrdersDetail(OrdersDetail ordersDetail) {
        return ordersDetailRepository.save(ordersDetail);
    }

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public List<OrdersDetail> getAllOrdersDetail() {
        return ordersDetailRepository.findAll();
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "D:\\Kuliah\\sib binar\\Chapter 4\\challenge4\\target";

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