package com.binaracademy.challenge5.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailResponse {
    private Long orderDetailId;
    private Long orderId;
    private Long productCode;
    private int quantity;
    private double totalPrice;
}
