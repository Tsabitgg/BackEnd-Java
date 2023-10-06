package com.binaracademy.challenge4.Model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCode;

    @Column
    private String productName;

    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "merchantCode")
    public Merchant merchant;
}
