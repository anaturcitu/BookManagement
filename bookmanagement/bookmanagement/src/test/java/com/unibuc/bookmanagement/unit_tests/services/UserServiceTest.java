package com.unibuc.bookmanagement.unit_tests.services;

import com.unibuc.bookmanagement.models.User;
import com.unibuc.bookmanagement.repositories.UserRepository;
import com.unibuc.bookmanagement.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findByEmail("user@example.com")).thenReturn(user);

        User result = userService.findByEmail("user@example.com");

        assertNotNull(result);
        assertEquals("user@example.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail("user@example.com");
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
