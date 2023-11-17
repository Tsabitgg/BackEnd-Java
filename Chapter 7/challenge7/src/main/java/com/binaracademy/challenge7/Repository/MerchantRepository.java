package com.binaracademy.challenge7.Repository;

import com.binaracademy.challenge7.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query("SELECT m FROM Merchant m WHERE m.merchantCode = :merchantCode")
    List<Merchant> findByMerchantCode(@Param("merchantCode") Long merchantCode);

    @Query("SELECT m FROM Merchant m WHERE m.open = true")
    List<Merchant> findOpenMerchants();

}

