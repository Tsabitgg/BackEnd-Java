package com.binaracademy.challenge6.Model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long merchantCode;

    @Column
    private String merchantName;

    @Column
    private String merchantLocation;

    @Column
    private boolean open;

}