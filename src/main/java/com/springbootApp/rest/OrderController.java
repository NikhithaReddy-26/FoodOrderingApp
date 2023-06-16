package com.springbootapp.rest;


import com.springbootapp.entity.FoodItem;
import com.springbootapp.entity.Order;
import com.springbootapp.entity.User;
import com.springbootapp.services.FoodItemService;
import com.springbootapp.services.OrderService;
import com.springbootapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final FoodItemService foodItemService;
    private final UserService userService;

public OrderController(OrderService orderService, FoodItemService foodItemService, UserService userService) {
    this.orderService = orderService;
    this.foodItemService = foodItemService;
    this.userService = userService;
}



    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//        Order createdOrder = orderService.createOrder(order);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
//    }
@PostMapping
public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    // Fetch the user and food items by their IDs
    Long userId = order.getUser().getId();
    User user = userService.getUserById(userId);

    List<FoodItem> foodItems = order.getFoodItems();
    for (FoodItem foodItem : foodItems) {
        Long foodItemId = foodItem.getId();
        FoodItem fetchedFoodItem = foodItemService.getFoodItemById(foodItemId);
        foodItem.setName(fetchedFoodItem.getName());
    }

    // Set the user and food item names in the order
    order.getUser().setName(user.getName());
    order.getFoodItems().forEach(foodItem -> foodItem.setName(foodItem.getName()));

    // Create the order
    Order createdOrder = orderService.createOrder(order);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
}

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        try {
            Order updatedOrder = orderService.updateOrder(id, order);
            return ResponseEntity.ok(updatedOrder);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
