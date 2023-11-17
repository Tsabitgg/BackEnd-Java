package com.binaracademy.challenge6.Service;

import com.binaracademy.challenge6.Model.User;
import com.binaracademy.challenge6.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public User updateUser(Long usersId, User updatedUser){
        User existingUser = userRepository.findById(usersId).get();
        updatedUser.setUserId(usersId);
        return userRepository.save(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).get();
        userRepository.delete(existingUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
