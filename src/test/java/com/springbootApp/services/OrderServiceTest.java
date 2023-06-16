package com.springbootapp.services;

import com.springbootapp.dao.OrderRepository;
        import com.springbootapp.entity.FoodItem;
        import com.springbootapp.entity.Order;
        import com.springbootapp.entity.User;
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

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> result = orderService.getAllOrders();

        // Assert
        assertEquals(orders, result);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_WithExistingId_ReturnsOrder() {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        Order result = orderService.getOrderById(orderId);

        // Assert
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void getOrderById_WithNonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        long nonExistingOrderId = 999L;
        when(orderRepository.findById(nonExistingOrderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(nonExistingOrderId));
        verify(orderRepository, times(1)).findById(nonExistingOrderId);
    }

    @Test
    void createOrder_ReturnsCreatedOrder() {
        // Arrange
        Order order = new Order();
        User user = new User();
        List<FoodItem> foodItems = new ArrayList<>();

        order.setUser(user);
        order.setFoodItems(foodItems);

        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order result = orderService.createOrder(order);

        // Assert
        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void updateOrder_ReturnsUpdatedOrder() {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order result = orderService.updateOrder(orderId, order);

        // Assert
        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void deleteOrder_DeletesOrder() {
        // Arrange
        long orderId = 1L;

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
