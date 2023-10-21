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

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long usersId;

    @Column
    private String usersname;

    @Column
    private String emailAddress;

    @Column
    public String password;
}

