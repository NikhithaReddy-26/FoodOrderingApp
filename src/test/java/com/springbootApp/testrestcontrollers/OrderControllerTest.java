package com.springbootapp.testrestcontrollers;

import com.springbootapp.entity.Order;
import com.springbootapp.rest.OrderController;
import com.springbootapp.services.FoodItemService;
        import com.springbootapp.services.OrderService;
        import com.springbootapp.services.UserService;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

        import java.util.Arrays;
        import java.util.List;
        import java.util.NoSuchElementException;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.mockito.Mockito.*;

class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @Mock
    private FoodItemService foodItemService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        // Arrange
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderService.getAllOrders()).thenReturn(orders);

        // Act
        List<Order> result = orderController.getAllOrders();

        // Assert
        assertEquals(orders, result);
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void getOrderById_WithExistingId_ReturnsOrder() {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        when(orderService.getOrderById(orderId)).thenReturn(order);

        // Act
        ResponseEntity<Order> result = orderController.getOrderById(orderId);

        // Assert
        assertEquals(ResponseEntity.ok(order), result);
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void getOrderById_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingOrderId = 999L;
        when(orderService.getOrderById(nonExistingOrderId)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<Order> result = orderController.getOrderById(nonExistingOrderId);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(orderService, times(1)).getOrderById(nonExistingOrderId);
    }

//    @Test
//    void createOrder_ReturnsCreatedOrder() {
//        // Arrange
//        long userId = 1L;
//        User user = new User(userId, "John");
//        long foodItemId = 1L;
//        FoodItem foodItem = new FoodItem(foodItemId, "Burger");
//        Order order = new Order();
//        order.setUser(user);
//        order.setFoodItems(Arrays.asList(foodItem));
//
//        when(userService.getUserById(userId)).thenReturn(user);
//        when(foodItemService.getFoodItemById(foodItemId)).thenReturn(foodItem);
//        when(orderService.createOrder(order)).thenReturn(order);
//
//        // Act
//        ResponseEntity<Order> result = orderController.createOrder(order);
//
//        // Assert
//        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(order), result);
//        verify(userService, times(1)).getUserById(userId);
//        verify(foodItemService, times(1)).getFoodItemById(foodItemId);
//        verify(orderService, times(1)).createOrder(order);
//    }

    @Test
    void updateOrder_WithExistingId_ReturnsUpdatedOrder() {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        when(orderService.updateOrder(orderId, order)).thenReturn(order);

        // Act
        ResponseEntity<Order> result = orderController.updateOrder(orderId, order);

        // Assert
        assertEquals(ResponseEntity.ok(order), result);
        verify(orderService, times(1)).updateOrder(orderId, order);
    }

    @Test
    void updateOrder_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingOrderId = 999L;
        Order order = new Order();
        when(orderService.updateOrder(nonExistingOrderId, order)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<Order> result = orderController.updateOrder(nonExistingOrderId, order);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(orderService, times(1)).updateOrder(nonExistingOrderId, order);
    }

    @Test
    void deleteOrder_WithExistingId_ReturnsNoContent() {
        // Arrange
        long orderId = 1L;

        // Act
        ResponseEntity<Void> result = orderController.deleteOrder(orderId);

        // Assert
        assertEquals(ResponseEntity.noContent().build(), result);
        verify(orderService, times(1)).deleteOrder(orderId);
    }

    @Test
    void deleteOrder_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        long nonExistingOrderId = 999L;
        doThrow(NoSuchElementException.class).when(orderService).deleteOrder(nonExistingOrderId);

        // Act
        ResponseEntity<Void> result = orderController.deleteOrder(nonExistingOrderId);

        // Assert
        assertEquals(ResponseEntity.notFound().build(), result);
        verify(orderService, times(1)).deleteOrder(nonExistingOrderId);
    }
}
