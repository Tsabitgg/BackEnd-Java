package com.binaracademy.challenge4.Service;

import com.binaracademy.challenge4.Model.Merchant;
import com.binaracademy.challenge4.Repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public Optional<Merchant> addMerchant(Merchant merchant) {
        try {
            Merchant addedMerchant = merchantRepository.save(merchant);
            return Optional.of(addedMerchant);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Merchant> updateMerchantStatus(Merchant merchant) {
            return Optional.of(merchantRepository.save(merchant));
    }

    @Override
    public List<Merchant> getOpenMerchants() {
        List<Merchant> allMerchants = merchantRepository.findAll();
        return allMerchants.stream()
                .filter(Merchant::isOpen)
                .collect(Collectors.toList());
    }

    @Override
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public Optional<Merchant> getMerchantByCode(Long merchantCode) {
        return merchantRepository.findById(merchantCode);
    }
}