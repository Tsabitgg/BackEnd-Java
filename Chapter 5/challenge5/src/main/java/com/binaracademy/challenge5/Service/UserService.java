package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.User;

import java.util.List;

public interface UserService {
    User updateUser(Long userId, User updatedUser) throws Exception;
    void deleteUser(Long userId);
    List<User> getAllUser();
}


