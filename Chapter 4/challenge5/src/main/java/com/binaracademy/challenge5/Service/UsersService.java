package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.Users;

import java.util.List;

public interface UsersService {
    Users addUsers(Users users);
    Users updateUsers(Long usersId, Users updatedUsers) throws Exception;
    void deleteUsers(Long usersId);
    List<Users> getAllUsers();
}


