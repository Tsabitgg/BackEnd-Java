package com.binaracademy.challenge5.Controller;

import com.binaracademy.challenge5.Model.Response.UsersResponse;
import com.binaracademy.challenge5.Model.Users;
import com.binaracademy.challenge5.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(value = "/add-users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UsersResponse> addUsers(@RequestBody Users user) {
        Users createdUser = usersService.addUsers(user);
        UsersResponse userResponse = createUserResponse(createdUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UsersResponse> updateUsers(@PathVariable Long userId, @RequestBody Users user) throws Exception {
        Users updatedUser = usersService.updateUsers(userId, user);
        UsersResponse userResponse = createUserResponse(updatedUser);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Long userId) {
        usersService.deleteUsers(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/get-all-users", produces = "application/json")
    public ResponseEntity<List<UsersResponse>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        List<UsersResponse> userResponses = users.stream()
                .map(this::createUserResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    // membuat objek UsersResponse dari entitas Users
    private UsersResponse createUserResponse(Users user) {
        UsersResponse userResponse = new UsersResponse();
        userResponse.setUserId(user.getUsersId());
        userResponse.setUsersname(user.getUsersname());
        userResponse.setEmailAddress(user.getEmailAddress());
        userResponse.setPassword(user.getPassword());
        return userResponse;
    }
}