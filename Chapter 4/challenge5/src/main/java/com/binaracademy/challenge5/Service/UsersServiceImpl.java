package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.Users;
import com.binaracademy.challenge5.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Users addUsers(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Users updateUsers(Long usersId, Users updatedUsers){
        Users existingUsers = usersRepository.findById(usersId).get();
        updatedUsers.setUsersId(usersId);
        return usersRepository.save(updatedUsers);
    }

    @Override
    public void deleteUsers(Long userId) {
        Users existingUser = usersRepository.findById(userId).get();
        usersRepository.delete(existingUser);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
