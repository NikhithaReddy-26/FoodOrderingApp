package com.springbootapp.servicetests;

import com.springbootapp.dao.FoodItemRepository;
        import com.springbootapp.entity.FoodItem;
        import com.springbootapp.services.FoodItemService;
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

class FoodItemServiceTest {

    @Mock
    private FoodItemRepository foodItemRepository;

    @InjectMocks
    private FoodItemService foodItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFoodItems_ReturnsListOfFoodItems() {
        // Arrange
        List<FoodItem> foodItems = new ArrayList<>();
        when(foodItemRepository.findAll()).thenReturn(foodItems);

        // Act
        List<FoodItem> result = foodItemService.getAllFoodItems();

        // Assert
        assertEquals(foodItems, result);
        verify(foodItemRepository, times(1)).findAll();
    }

    @Test
    void getFoodItemById_WithExistingId_ReturnsFoodItem() {
        // Arrange
        long foodItemId = 1L;
        FoodItem foodItem = new FoodItem();
        when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.of(foodItem));

        // Act
        FoodItem result = foodItemService.getFoodItemById(foodItemId);

        // Assert
        assertEquals(foodItem, result);
        verify(foodItemRepository, times(1)).findById(foodItemId);
    }

    @Test
    void getFoodItemById_WithNonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        long nonExistingFoodItemId = 999L;
        when(foodItemRepository.findById(nonExistingFoodItemId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> foodItemService.getFoodItemById(nonExistingFoodItemId));
        verify(foodItemRepository, times(1)).findById(nonExistingFoodItemId);
    }

    @Test
    void createFoodItem_ReturnsCreatedFoodItem() {
        // Arrange
        FoodItem foodItem = new FoodItem();
        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);

        // Act
        FoodItem result = foodItemService.createFoodItem(foodItem);

        // Assert
        assertEquals(foodItem, result);
        verify(foodItemRepository, times(1)).save(foodItem);
    }

    @Test
    void updateFoodItem_WithExistingId_ReturnsUpdatedFoodItem() {
        // Arrange
        long foodItemId = 1L;
        FoodItem existingFoodItem = new FoodItem();
        FoodItem updatedFoodItem = new FoodItem();
        when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.of(existingFoodItem));
        when(foodItemRepository.save(existingFoodItem)).thenReturn(updatedFoodItem);

        // Act
        FoodItem result = foodItemService.updateFoodItem(foodItemId, existingFoodItem);

        // Assert
        assertEquals(updatedFoodItem, result);
        verify(foodItemRepository, times(1)).findById(foodItemId);
        verify(foodItemRepository, times(1)).save(existingFoodItem);
    }

    @Test
    void updateFoodItem_WithNonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        long nonExistingFoodItemId = 999L;
        FoodItem foodItem = new FoodItem();
        when(foodItemRepository.findById(nonExistingFoodItemId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> foodItemService.updateFoodItem(nonExistingFoodItemId, foodItem));
        verify(foodItemRepository, times(1)).findById(nonExistingFoodItemId);
        verify(foodItemRepository, never()).save(any(FoodItem.class));
    }

    @Test
    void deleteFoodItem_DeletesFoodItem() {
        // Arrange
        long foodItemId = 1L;

        // Act
        foodItemService.deleteFoodItem(foodItemId);

        // Assert
        verify(foodItemRepository, times(1)).deleteById(foodItemId);
    }
}
