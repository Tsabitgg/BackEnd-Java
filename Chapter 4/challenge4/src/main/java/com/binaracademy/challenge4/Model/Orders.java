package com.binaracademy.challenge4.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column
    private Date orderTime;

    @Column
    private String destinationAddress;

    @ManyToOne
    @JoinColumn(name = "usersId")
    public Users users;

    @Column
    private boolean completed;
}