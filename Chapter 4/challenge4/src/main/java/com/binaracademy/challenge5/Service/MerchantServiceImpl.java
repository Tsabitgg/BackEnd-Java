package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.Merchant;
import com.binaracademy.challenge5.Repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public Merchant addMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    //update status berdasarkan code
    @Override
    public Merchant updateMerchantStatus(Long merchantCode, boolean open) throws Exception {
        Merchant existingMerchant = (Merchant) merchantRepository.findByMerchantCode(merchantCode); //mencari berdasarkan code
        if (existingMerchant != null) {                                                             //jika merchant ditemukan, update statusnya dan disimpan
            existingMerchant.setOpen(open);
            return merchantRepository.save(existingMerchant);
        }
        throw new Exception("Merchant with code " + merchantCode + " not found.");                  //jika tidak, maka keluar exception
    }

    @Override
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public List<Merchant> getOpenMerchants() {
        return merchantRepository.findOpenMerchants();
    }
}
