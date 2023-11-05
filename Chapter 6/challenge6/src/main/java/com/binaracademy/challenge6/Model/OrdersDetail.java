package com.binaracademy.challenge6.Model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_code", referencedColumnName = "productCode")
    private Product product;

    private int quantity;
    private double totalPrice;

}