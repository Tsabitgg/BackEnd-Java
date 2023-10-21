package com.binaracademy.challenge5.Model.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
    private Long userId;
    private String usersname;
    private String emailAddress;
    private String password;
}
