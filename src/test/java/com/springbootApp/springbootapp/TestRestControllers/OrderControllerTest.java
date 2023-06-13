package com.springbootApp.springbootapp.TestRestControllers;


        import com.springbootApp.springbootapp.entity.Order;
        import com.springbootApp.springbootapp.rest.OrderController;
        import com.springbootApp.springbootapp.services.OrderService;
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

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        when(orderService.getAllOrders()).thenReturn(orders);

        // Act
        List<Order> result = orderController.getAllOrders();

        // Assert
        assertEquals(orders, result);
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testGetOrderById() {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        when(orderService.getOrderById(orderId)).thenReturn(order);

        // Act
        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        // Assert
        assertEquals(order, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void testGetOrderByIdNotFound() {
        // Arrange
        long orderId = 1L;
        when(orderService.getOrderById(orderId)).thenThrow(new NoSuchElementException());

        // Act
        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        Order order = new Order();
        when(orderService.createOrder(order)).thenReturn(order);

        // Act
        ResponseEntity<Order> response = orderController.createOrder(order);

        // Assert
        assertEquals(order, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(orderService, times(1)).createOrder(order);
    }

    @Test
    void testUpdateOrder() {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        when(orderService.updateOrder(orderId, order)).thenReturn(order);

        // Act
        ResponseEntity<Order> response = orderController.updateOrder(orderId, order);

        // Assert
        assertEquals(order, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).updateOrder(orderId, order);
    }

    @Test
    void testUpdateOrderNotFound() {
        // Arrange
        long orderId = 1L;
        Order order = new Order();
        when(orderService.updateOrder(orderId, order)).thenThrow(new NoSuchElementException());

        // Act
        ResponseEntity<Order> response = orderController.updateOrder(orderId, order);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).updateOrder(orderId, order);
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        long orderId = 1L;

        // Act
        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).deleteOrder(orderId);
    }

    @Test
    void testDeleteOrderNotFound() {
        // Arrange
        long orderId = 1L;
        doThrow(new NoSuchElementException()).when(orderService).deleteOrder(orderId);

        // Act
        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(orderService, times(1)).deleteOrder(orderId);
    }
}
