package com.binaracademy.challenge4.Service;

import com.binaracademy.challenge4.Model.Users;
import com.binaracademy.challenge4.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Users> addUser(Users users) {
        try {
            Users addedUsers = userRepository.save(users);
            return Optional.of(addedUsers);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Users> updateUser(Users users) {
        return Optional.of(userRepository.save(users));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}