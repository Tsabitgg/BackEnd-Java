package com.binaracademy.challenge6.Model.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantResponse {
    private Long merchantCode;
    private String merchantName;
    private String merchantLocation;
    private boolean open;
}
