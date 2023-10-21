package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.Merchant;

import java.util.List;

public interface MerchantService {
    Merchant addMerchant(Merchant merchant);
    Merchant updateMerchantStatus(Long merchantCode, boolean open) throws Exception;
    List<Merchant> getAllMerchants();
    List<Merchant> getOpenMerchants();

}
