package com.binaracademy.challenge7.Service;

import com.binaracademy.challenge7.Model.Merchant;
import com.binaracademy.challenge7.Repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    @Transactional
    @Override
    public Merchant addMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    //update status berdasarkan code
    @Transactional
    @Override
    public Merchant updateMerchantStatus(Long merchantCode, boolean open) throws Exception {
        Merchant existingMerchant = (Merchant) merchantRepository.findByMerchantCode(merchantCode); //mencari berdasarkan code
        if (existingMerchant != null) {                                                             //jika merchant ditemukan, update statusnya dan disimpan
            existingMerchant.setOpen(open);
            return merchantRepository.save(existingMerchant);
        }
        throw new Exception("Merchant with code " + merchantCode + " not found.");                  //jika tidak, maka keluar exception
    }

    @Transactional(readOnly = true)
    @Override
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Merchant> getOpenMerchants() {
        return merchantRepository.findOpenMerchants();
    }
}
