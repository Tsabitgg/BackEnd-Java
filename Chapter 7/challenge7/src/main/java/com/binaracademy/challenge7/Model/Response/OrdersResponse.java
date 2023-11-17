package com.binaracademy.challenge7.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    private Long orderId;
    private Date orderTime;
    private String destinationAddress;
    private Long userId;
    private boolean completed;
}
