package com.binaracademy.challenge4.Repository;

import com.binaracademy.challenge4.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query("SELECT merchantCode, merchantName, merchantLocation FROM Merchant ")
    List<Merchant> findByMerchantName(String merchantName);

}

