package com.springbootApp.springbootapp.TestRestControllers;


import com.springbootApp.springbootapp.entity.FoodItem;
import com.springbootApp.springbootapp.rest.FoodItemController;
import com.springbootApp.springbootapp.services.FoodItemService;
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

class FoodItemControllerTest {

    @Mock
    private FoodItemService foodItemService;

    private FoodItemController foodItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foodItemController = new FoodItemController(foodItemService);
    }

    @Test
    void testGetAllFoodItems() {
        // Arrange
        List<FoodItem> foodItems = new ArrayList<>();
        when(foodItemService.getAllFoodItems()).thenReturn(foodItems);

        // Act
        List<FoodItem> result = foodItemController.getAllFoodItems();

        // Assert
        assertEquals(foodItems, result);
        verify(foodItemService, times(1)).getAllFoodItems();
    }

    @Test
    void testGetFoodItemById() {
        // Arrange
        long foodItemId = 1L;
        FoodItem foodItem = new FoodItem();
        when(foodItemService.getFoodItemById(foodItemId)).thenReturn(foodItem);

        // Act
        ResponseEntity<FoodItem> response = foodItemController.getFoodItemById(foodItemId);

        // Assert
        assertEquals(foodItem, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(foodItemService, times(1)).getFoodItemById(foodItemId);
    }

    @Test
    void testGetFoodItemByIdNotFound() {
        // Arrange
        long foodItemId = 1L;
        when(foodItemService.getFoodItemById(foodItemId)).thenThrow(new NoSuchElementException());

        // Act
        ResponseEntity<FoodItem> response = foodItemController.getFoodItemById(foodItemId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(foodItemService, times(1)).getFoodItemById(foodItemId);
    }

    @Test
    void testCreateFoodItem() {
        // Arrange
        FoodItem foodItem = new FoodItem();
        when(foodItemService.createFoodItem(foodItem)).thenReturn(foodItem);

        // Act
        ResponseEntity<FoodItem> response = foodItemController.createFoodItem(foodItem);

        // Assert
        assertEquals(foodItem, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(foodItemService, times(1)).createFoodItem(foodItem);
    }

    @Test
    void testUpdateFoodItem() {
        // Arrange
        long foodItemId = 1L;
        FoodItem foodItem = new FoodItem();
        when(foodItemService.updateFoodItem(foodItemId, foodItem)).thenReturn(foodItem);

        // Act
        ResponseEntity<FoodItem> response = foodItemController.updateFoodItem(foodItemId, foodItem);

        // Assert
        assertEquals(foodItem, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(foodItemService, times(1)).updateFoodItem(foodItemId, foodItem);
    }

    @Test
    void testUpdateFoodItemNotFound() {
        // Arrange
        long foodItemId = 1L;
        FoodItem foodItem = new FoodItem();
        when(foodItemService.updateFoodItem(foodItemId, foodItem)).thenThrow(new NoSuchElementException());

        // Act
        ResponseEntity<FoodItem> response = foodItemController.updateFoodItem(foodItemId, foodItem);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(foodItemService, times(1)).updateFoodItem(foodItemId, foodItem);
    }

    @Test
    void testDeleteFoodItem() {
        // Arrange
        long foodItemId = 1L;

        // Act
        ResponseEntity<Void> response = foodItemController.deleteFoodItem(foodItemId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(foodItemService, times(1)).deleteFoodItem(foodItemId);
    }

    @Test
    void testDeleteFoodItemNotFound() {
        // Arrange
        long foodItemId = 1L;
        doThrow(new NoSuchElementException()).when(foodItemService).deleteFoodItem(foodItemId);

        // Act
        ResponseEntity<Void> response = foodItemController.deleteFoodItem(foodItemId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(foodItemService, times(1)).deleteFoodItem(foodItemId);
    }
}
