package com.springbootApp.springbootapp.TestRestControllers;



        import com.springbootApp.springbootapp.entity.User;
        import com.springbootApp.springbootapp.rest.UserController;
        import com.springbootApp.springbootapp.services.UserService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
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

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void testGetAllUsers() {
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
    void testGetUserById() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUserById(userId);

        // Assert
        assertEquals(user, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserByIdNotFound() {
        // Arrange
        long userId = 1L;
        when(userService.getUserById(userId)).thenThrow(new NoSuchElementException());

        // Act
        ResponseEntity<User> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testCreateUser() {
        // Arrange
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.createUser(user);

        // Assert
        assertEquals(user, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userService.updateUser(userId, user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, user);

        // Assert
        assertEquals(user, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).updateUser(userId, user);
    }

    @Test
    void testUpdateUserNotFound() {
        // Arrange
        long userId = 1L;
        User user = new User();
        when(userService.updateUser(userId, user)).thenThrow(new NoSuchElementException());

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, user);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser(userId, user);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        long userId = 1L;

        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        // Arrange
        long userId = 1L;
        doThrow(new NoSuchElementException()).when(userService).deleteUser(userId);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
