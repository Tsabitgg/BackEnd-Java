package com.binaracademy.challenge5.ServiceTest;

import com.binaracademy.challenge5.Model.User;
import com.binaracademy.challenge5.Repository.UserRepository;
import com.binaracademy.challenge5.Service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 123L;
        User updatedUser = new User();
        updatedUser.setUserId(userId);

        User existingUser = new User();
        existingUser.setUserId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertEquals(updatedUser, result);
    }

    @Test(expected = Exception.class)
    public void testUpdateUserUserNotFound() {
        Long userId = 123L;
        User updatedUser = new User();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.updateUser(userId, updatedUser);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 123L;
        User existingUser = new User();
        existingUser.setUserId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        Mockito.verify(userRepository).delete(existingUser);
    }

    @Test(expected = Exception.class)
    public void testDeleteUserUserNotFound() {
        Long userId = 123L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        userService.deleteUser(userId);
    }

    @Test
    public void testGetAllUser() {
        List<User> userList = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUser();

        assertEquals(userList, result);
    }
}
