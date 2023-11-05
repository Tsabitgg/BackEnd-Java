package com.binaracademy.challenge6.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long productCode;
    private String productName;
    private double price;
    private Long merchantCode;
}
