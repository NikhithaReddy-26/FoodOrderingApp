package com.springbootapp.services;

import com.springbootapp.entity.User;
import com.springbootapp.dao.UserRepository;
import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.NoSuchElementException;
        import java.util.Optional;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(users, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WithExistingId_ReturnsUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_WithNonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        long nonExistingUserId = 999L;
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(nonExistingUserId));
        verify(userRepository, times(1)).findById(nonExistingUserId);
    }

    @Test
    void createUser_ReturnsCreatedUser() {
        // Arrange
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_ReturnsUpdatedUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.updateUser(userId, user);

        // Assert
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_DeletesUser() {
        // Arrange
        long userId = 1L;

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }
}
