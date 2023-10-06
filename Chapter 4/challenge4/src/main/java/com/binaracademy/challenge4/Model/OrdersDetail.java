package com.binaracademy.challenge4.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_code", referencedColumnName = "productCode")
    private Product product;

    private int quantity;
    private double totalPrice;

   // public OrdersDetail(Long product, int quantity) {
    //}

}