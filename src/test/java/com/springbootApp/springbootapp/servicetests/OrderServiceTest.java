package com.springbootApp.springbootapp.servicetests;



        import com.springbootApp.springbootapp.dao.OrderRepository;
        import com.springbootApp.springbootapp.entity.Order;
        import com.springbootApp.springbootapp.services.OrderService;
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

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void testGetAllOrders() {
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
    void testGetOrderById() {
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
    void testGetOrderByIdNotFound() {
        // Arrange
        long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order result = orderService.createOrder(order);

        // Assert
        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrder() {
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
    void testDeleteOrder() {
        // Arrange
        long orderId = 1L;

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
