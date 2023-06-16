package com.springbootapp.testrestcontrollers;

import com.springbootapp.entity.User;
import com.springbootapp.rest.UserController;
import com.springbootapp.services.UserService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.NoSuchElementException;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        List<User> result = userController.getAllUsers();

        // Assert
        assertEquals(users, result);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_WithExistingId_ReturnsUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<User> result = userController.getUserById(userId);

        // Assert
        assertEquals(ResponseEntity.ok(user), result);
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserById_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingUserId = 999L;
        when(userService.getUserById(nonExistingUserId)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<User> result = userController.getUserById(nonExistingUserId);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(userService, times(1)).getUserById(nonExistingUserId);
    }

    @Test
    void createUser_ReturnsCreatedUser() {
        // Arrange
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        // Act
        ResponseEntity<User> result = userController.createUser(user);

        // Assert
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(user), result);
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void updateUser_WithExistingId_ReturnsUpdatedUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userService.updateUser(userId, user)).thenReturn(user);

        // Act
        ResponseEntity<User> result = userController.updateUser(userId, user);

        // Assert
        assertEquals(ResponseEntity.ok(user), result);
        verify(userService, times(1)).updateUser(userId, user);
    }

    @Test
    void updateUser_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingUserId = 999L;
        User user = new User();
        when(userService.updateUser(nonExistingUserId, user)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<User> result = userController.updateUser(nonExistingUserId, user);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(userService, times(1)).updateUser(nonExistingUserId, user);
    }

    @Test
    void deleteUser_WithExistingId_ReturnsNoContent() {
        // Arrange
        long userId = 1L;

        // Act
        ResponseEntity<Void> result = userController.deleteUser(userId);

        // Assert
        assertEquals(ResponseEntity.noContent().build(), result);
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void deleteUser_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingUserId = 999L;
        doThrow(NoSuchElementException.class).when(userService).deleteUser(nonExistingUserId);

        // Act
        ResponseEntity<Void> result = userController.deleteUser(nonExistingUserId);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(userService, times(1)).deleteUser(nonExistingUserId);
    }
}
