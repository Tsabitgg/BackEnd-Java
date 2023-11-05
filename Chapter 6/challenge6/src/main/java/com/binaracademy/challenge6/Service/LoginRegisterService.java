package com.binaracademy.challenge6.Service;

import com.binaracademy.challenge6.Model.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface LoginRegisterService {

    public void registerOauth2User(Authentication authentication);
    public Optional<User> loginOauth2User(Authentication authentication);
}