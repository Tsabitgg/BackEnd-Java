package com.binaracademy.challenge5.Controller;

import com.binaracademy.challenge5.Model.Response.UserResponse;
import com.binaracademy.challenge5.Model.User;
import com.binaracademy.challenge5.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/update/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponse> updateUsers(@PathVariable Long userId, @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(userId, user);
        UserResponse userResponse = createUserResponse(updatedUser);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/get-all-user", produces = "application/json")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> user = userService.getAllUser();
        List<UserResponse> userResponses = user.stream()
                .map(this::createUserResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    // membuat objek UserResponse dari entitas User
    private UserResponse createUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        return userResponse;
    }
}