package com.binaracademy.challenge5.Service;

import com.binaracademy.challenge5.Model.User;
import com.binaracademy.challenge5.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User updateUser(Long usersId, User updatedUser){
        User existingUser = userRepository.findById(usersId).get();
        updatedUser.setUserId(usersId);
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).get();
        userRepository.delete(existingUser);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
