package com.binaracademy.challenge5.ControllerTest;

import com.binaracademy.challenge5.Controller.UserController;
import com.binaracademy.challenge5.Model.Response.UserResponse;
import com.binaracademy.challenge5.Model.User;
import com.binaracademy.challenge5.Service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateUsers() throws Exception {
        Long userId = 123L;
        User user = new User();
        User updatedUser = new User();
        Mockito.when(userService.updateUser(eq(userId), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<UserResponse> response = userController.updateUsers(userId, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 123L;

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        Mockito.when(userService.getAllUser()).thenReturn(users);

        ResponseEntity<List<UserResponse>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
