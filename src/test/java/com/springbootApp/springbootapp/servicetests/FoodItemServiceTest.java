package com.springbootApp.springbootapp.servicetests;


        import com.springbootApp.springbootapp.dao.FoodItemRepository;
        import com.springbootApp.springbootapp.entity.FoodItem;
        import com.springbootApp.springbootapp.services.FoodItemService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.NoSuchElementException;
        import java.util.Optional;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertThrows;
        import static org.mockito.Mockito.*;

class FoodItemServiceTest {

    @Mock
    private FoodItemRepository foodItemRepository;

    private FoodItemService foodItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foodItemService = new FoodItemService(foodItemRepository);
    }

    @Test
    void testGetAllFoodItems() {
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
    void testGetFoodItemById() {
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
    void testGetFoodItemByIdNotFound() {
        // Arrange
        long foodItemId = 1L;
        when(foodItemRepository.findById(foodItemId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> foodItemService.getFoodItemById(foodItemId));
        verify(foodItemRepository, times(1)).findById(foodItemId);
    }

    @Test
    void testCreateFoodItem() {
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
    void testUpdateFoodItem() {
        // Arrange
        long foodItemId = 1L;
        FoodItem foodItem = new FoodItem();
        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);

        // Act
        FoodItem result = foodItemService.updateFoodItem(foodItemId, foodItem);

        // Assert
        assertEquals(foodItem, result);
        verify(foodItemRepository, times(1)).save(foodItem);
    }

    @Test
    void testDeleteFoodItem() {
        // Arrange
        long foodItemId = 1L;

        // Act
        foodItemService.deleteFoodItem(foodItemId);

        // Assert
        verify(foodItemRepository, times(1)).deleteById(foodItemId);
    }
}
