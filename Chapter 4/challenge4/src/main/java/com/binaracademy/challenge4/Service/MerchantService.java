package com.binaracademy.challenge4.Service;

import com.binaracademy.challenge4.Model.Merchant;

import java.util.List;
import java.util.Optional;

public interface MerchantService {
    Optional<Merchant> addMerchant(Merchant merchant);
    Optional<Merchant> updateMerchantStatus(Merchant merchant);
    List<Merchant> getOpenMerchants();
    List<Merchant> getAllMerchants();
    Optional<Merchant> getMerchantByCode(Long merchantCode);
}