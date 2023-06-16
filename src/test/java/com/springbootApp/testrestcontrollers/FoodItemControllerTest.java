package com.springbootapp.testrestcontrollers;


import com.springbootapp.entity.FoodItem;
import com.springbootapp.rest.FoodItemController;
import com.springbootapp.services.FoodItemService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;

        import java.util.Arrays;
        import java.util.List;
        import java.util.NoSuchElementException;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.Mockito.*;

class FoodItemControllerTest {
    @Mock
    private FoodItemService foodItemService;

    @InjectMocks
    private FoodItemController foodItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFoodItems_ReturnsListOfFoodItems() {
        // Arrange
        FoodItem foodItem1 = new FoodItem(1L, "Item 1");
        FoodItem foodItem2 = new FoodItem(2L, "Item 2");
        List<FoodItem> foodItems = Arrays.asList(foodItem1, foodItem2);
        when(foodItemService.getAllFoodItems()).thenReturn(foodItems);

        // Act
        List<FoodItem> result = foodItemController.getAllFoodItems();

        // Assert
        assertEquals(foodItems, result);
        verify(foodItemService, times(1)).getAllFoodItems();
    }

    @Test
    void getFoodItemById_WithExistingId_ReturnsFoodItem() {
        // Arrange
        long itemId = 1L;
        FoodItem foodItem = new FoodItem(itemId, "Item 1");
        when(foodItemService.getFoodItemById(itemId)).thenReturn(foodItem);

        // Act
        ResponseEntity<FoodItem> result = foodItemController.getFoodItemById(itemId);

        // Assert
        assertEquals(ResponseEntity.ok(foodItem), result);
        verify(foodItemService, times(1)).getFoodItemById(itemId);
    }

    @Test
    void getFoodItemById_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingItemId = 999L;
        when(foodItemService.getFoodItemById(nonExistingItemId)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<FoodItem> result = foodItemController.getFoodItemById(nonExistingItemId);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(foodItemService, times(1)).getFoodItemById(nonExistingItemId);
    }

    @Test
    void createFoodItem_ReturnsCreatedFoodItem() {
        // Arrange
        FoodItem foodItem = new FoodItem(1L, "New Item");
        when(foodItemService.createFoodItem(foodItem)).thenReturn(foodItem);

        // Act
        ResponseEntity<FoodItem> result = foodItemController.createFoodItem(foodItem);

        // Assert
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(foodItem), result);
        verify(foodItemService, times(1)).createFoodItem(foodItem);
    }

    @Test
    void updateFoodItem_WithExistingId_ReturnsUpdatedFoodItem() {
        // Arrange
        long itemId = 1L;
        FoodItem existingFoodItem = new FoodItem(itemId, "Item 1");
        FoodItem updatedFoodItem = new FoodItem(itemId, "Updated Item");
        when(foodItemService.updateFoodItem(itemId, updatedFoodItem)).thenReturn(updatedFoodItem);

        // Act
        ResponseEntity<FoodItem> result = foodItemController.updateFoodItem(itemId, updatedFoodItem);

        // Assert
        assertEquals(ResponseEntity.ok(updatedFoodItem), result);
        verify(foodItemService, times(1)).updateFoodItem(itemId, updatedFoodItem);
    }

    @Test
    void updateFoodItem_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingItemId = 999L;
        FoodItem updatedFoodItem = new FoodItem(nonExistingItemId, "Updated Item");
        when(foodItemService.updateFoodItem(nonExistingItemId, updatedFoodItem))
                .thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<FoodItem> result = foodItemController.updateFoodItem(nonExistingItemId, updatedFoodItem);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(foodItemService, times(1)).updateFoodItem(nonExistingItemId, updatedFoodItem);
    }

    @Test
    void deleteFoodItem_WithExistingId_ReturnsNoContent() {
        // Arrange
        long itemId = 1L;

        // Act
        ResponseEntity<Void> result = foodItemController.deleteFoodItem(itemId);

        // Assert
        assertEquals(ResponseEntity.noContent().build(), result);
        verify(foodItemService, times(1)).deleteFoodItem(itemId);
    }

    @Test
    void deleteFoodItem_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingItemId = 999L;
        doThrow(NoSuchElementException.class).when(foodItemService).deleteFoodItem(nonExistingItemId);

        // Act
        ResponseEntity<Void> result = foodItemController.deleteFoodItem(nonExistingItemId);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(foodItemService, times(1)).deleteFoodItem(nonExistingItemId);
    }
}
