package com.binaracademy.challenge4.Service;

import com.binaracademy.challenge4.Model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<Users> addUser(Users users);
    Optional<Users> updateUser(Users users);
    void deleteUser(Long userId);
    List<Users> getAllUsers();
    Optional<Users> getUserById(Long userId);
}

