package com.binaracademy.challenge5.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productCode;

    @Column
    private String productName;

    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "merchant_code")
    public Merchant merchant;

}
