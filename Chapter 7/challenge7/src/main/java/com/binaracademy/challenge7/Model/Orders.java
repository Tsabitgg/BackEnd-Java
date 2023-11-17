package com.binaracademy.challenge7.Model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column
    private Date orderTime;

    @Column
    private String destinationAddress;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "userId")
    public User user;

    @Column
    private boolean completed;
}